package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wizard.software.diet.meals.MealDao;
import pl.wizard.software.diet.meals.MealEntity;
import pl.wizard.software.diet.meals.MealTimeEnum;
import pl.wizard.software.diet.schedules.ScheduleDao;
import pl.wizard.software.diet.schedules.ScheduleEntity;
import pl.wizard.software.dto.CreateScheduleDto;
import pl.wizard.software.dto.CreateScheduleForDayDto;
import pl.wizard.software.dto.ScheduleForDayDto;
import pl.wizard.software.dto.ScheduleForWeekDto;
import pl.wizard.software.mapper.ScheduleDtoMapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.wizard.software.diet.meals.MealTimeEnum.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleService {

    public static final int DAYS_IN_WEEK = 7;
    private final MealDao mealRepository;
    private final ScheduleDao scheduleRepository;

    public ScheduleForWeekDto getScheduleByMealTime() {
        ScheduleForWeekDto schedule = new ScheduleForWeekDto();
        LocalDate nextWeekMonday = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().ordinal()).plusDays(DAYS_IN_WEEK);
        for (DayOfWeek day : DayOfWeek.values()) {
            ScheduleForDayDto scheduleForDayDto = new ScheduleForDayDto();
            scheduleForDayDto.setDate(nextWeekMonday.plusDays(day.ordinal()));
            schedule.getSchedule().add(scheduleForDayDto);
        }
        for (int i = 1; i < values().length; i++) {
            List<MealEntity> meals = mealRepository.findRandomByMealTime(values()[i].ordinal(), DAYS_IN_WEEK);
            for (DayOfWeek day : DayOfWeek.values()) {
                Optional<MealEntity> meal = meals.stream().findFirst();
                if (meal.isPresent()) {
                    schedule.getSchedule().get(day.ordinal()).add(meal.get(), values()[i]);
                    meals.remove(meal.get());
                }
            }
        }
        return schedule;
    }

    public ScheduleForWeekDto createSchedule(CreateScheduleDto schedule) {
        LocalDate scheduleDate = schedule.getSchedule().stream()
                .map(CreateScheduleForDayDto::getDate)
                .sorted()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no date in create schedule"));

        List<ScheduleForDayDto> scheduleForDayDtos = new ArrayList<>();
        for (CreateScheduleForDayDto createScheduleForDayDto : schedule.getSchedule()) {
            ScheduleForDayDto scheduleForDayDto = new ScheduleForDayDto();
            scheduleForDayDto.setDate(createScheduleForDayDto.getDate());
            addMeal(createScheduleForDayDto.getBreakfast(), BREAKFAST, scheduleForDayDto);
            addMeal(createScheduleForDayDto.getSecondBreakfast(), SECOND_BREAKFAST, scheduleForDayDto);
            addMeal(createScheduleForDayDto.getLunch(), LUNCH, scheduleForDayDto);
            addMeal(createScheduleForDayDto.getDinner(), DINNER, scheduleForDayDto);
            addMeal(createScheduleForDayDto.getSupper(), SUPPER, scheduleForDayDto);
            scheduleForDayDtos.add(scheduleForDayDto);
        }

        ScheduleEntity scheduleEntity = ScheduleEntity.builder()
                .memberId(schedule.getMemberId())
                .scheduleDate(scheduleDate)
                .schedule(ScheduleDtoMapper.convertToBytes(scheduleForDayDtos))
                .build();

        return ScheduleDtoMapper.mapToScheduleDto(scheduleRepository.save(scheduleEntity));
    }

    private void addMeal(Long mealId, MealTimeEnum mealTime, ScheduleForDayDto scheduleForDayDto) {
        Optional<MealEntity> mealEntity = mealRepository.findById(mealId);
        if (mealEntity.isEmpty()) {
            log.error("Meal with id " + mealId + "does not exists");
        } else {
            scheduleForDayDto.add(mealEntity.get(), mealTime);
        }
    }

    @Transactional
    public List<ScheduleForWeekDto> findAll(Long accountId) {
        return scheduleRepository.findAll(accountId).stream()
                .map(ScheduleDtoMapper::mapToScheduleDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<ScheduleForWeekDto> findById(Long accountId, Long scheduleId) {
        Optional<ScheduleEntity> schedule = scheduleRepository.findById(accountId, scheduleId);
        return getScheduleForWeekDto(schedule);
    }

    @Transactional
    public Optional<ScheduleForWeekDto> findByMember(Long accountId, Long memberId) {
        Pageable topOne = PageRequest.of(0, 1);
        Optional<ScheduleEntity> schedule = scheduleRepository.findByMember(accountId, memberId, topOne).stream().findFirst();
        return getScheduleForWeekDto(schedule);
    }

    public ScheduleForWeekDto save(ScheduleForWeekDto schedule) {
        ScheduleEntity scheduleEntity = ScheduleDtoMapper.mapToScheduleEntity(schedule);
        return ScheduleDtoMapper.mapToScheduleDto(scheduleRepository.save(scheduleEntity));
    }

    public void deleteById(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    private Optional<ScheduleForWeekDto> getScheduleForWeekDto(Optional<ScheduleEntity> schedule) {
        return schedule.map(ScheduleDtoMapper::mapToScheduleDto);
    }

}
