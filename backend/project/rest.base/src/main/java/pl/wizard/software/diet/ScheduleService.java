package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wizard.software.diet.meals.MealEntity;
import pl.wizard.software.diet.meals.MealTimeEnum;
import pl.wizard.software.diet.members.MemberDao;
import pl.wizard.software.diet.members.MemberEntity;
import pl.wizard.software.diet.schedules.ScheduleDao;
import pl.wizard.software.diet.schedules.ScheduleEntity;
import pl.wizard.software.dto.CreateScheduleDto;
import pl.wizard.software.dto.CreateScheduleForDayDto;
import pl.wizard.software.dto.ScheduleForDayDto;
import pl.wizard.software.dto.ScheduleForWeekDto;
import pl.wizard.software.mapper.ScheduleDtoMapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import static pl.wizard.software.diet.meals.MealTimeEnum.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleService {

    public static final int DAYS_IN_WEEK = 7;
    private final MealService mealService;
    private final ScheduleDao scheduleRepository;
    private final MemberDao memberRepository;

    public ScheduleForWeekDto getScheduleByMealTime() {
        ScheduleForWeekDto schedule = new ScheduleForWeekDto();
        LocalDate nextWeekMonday = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().ordinal()).plusDays(DAYS_IN_WEEK);
        for (DayOfWeek day : DayOfWeek.values()) {
            ScheduleForDayDto scheduleForDayDto = new ScheduleForDayDto();
            scheduleForDayDto.setDate(nextWeekMonday.plusDays(day.ordinal()));
            schedule.getSchedule().add(scheduleForDayDto);
        }
        for (int i = 1; i < values().length; i++) {
            List<MealEntity> meals = mealService.findRandomByMealTime(values()[i].ordinal(), DAYS_IN_WEEK);
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

    public ScheduleEntity createSchedule(CreateScheduleDto schedule) {
        MemberEntity member = memberRepository.findById(schedule.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("Could not find member with id " + schedule.getMemberId()));

        LocalDate scheduleDate = schedule.getSchedule().stream()
                .map(CreateScheduleForDayDto::getDate)
                .sorted()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no date in create schedule"));

        List<ScheduleForDayDto> scheduleForDayDtos = new ArrayList<>();
        for (CreateScheduleForDayDto createScheduleForDayDto : schedule.getSchedule()) {
            ScheduleForDayDto scheduleForDayDto = new ScheduleForDayDto();
            scheduleForDayDto.setDate(createScheduleForDayDto.getDate());
            addMeal(createScheduleForDayDto.getBreakfast(), BREAKFAST, member.getId(), scheduleForDayDto);
            addMeal(createScheduleForDayDto.getSecondBreakfast(), SECOND_BREAKFAST, member.getId(), scheduleForDayDto);
            addMeal(createScheduleForDayDto.getLunch(), LUNCH, member.getId(), scheduleForDayDto);
            addMeal(createScheduleForDayDto.getDinner(), DINNER, member.getId(), scheduleForDayDto);
            addMeal(createScheduleForDayDto.getSupper(), SUPPER, member.getId(), scheduleForDayDto);
            scheduleForDayDtos.add(scheduleForDayDto);
        }

        ScheduleEntity scheduleEntity = ScheduleEntity.builder()
                .memberId(schedule.getMemberId())
                .scheduleDate(scheduleDate)
                .schedule(ScheduleDtoMapper.convertToBytes(scheduleForDayDtos))
                .build();

        return scheduleRepository.save(scheduleEntity);
    }

    private void addMeal(Long mealId, MealTimeEnum mealTime, Long memberId, ScheduleForDayDto scheduleForDayDto) {
        MealEntity mealEntity = mealService.findByIdAndMember(mealId, memberId);
        scheduleForDayDto.add(mealEntity, mealTime);
    }

    @Transactional
    public ScheduleEntity update(Long accountId, Long scheduleId, ScheduleForWeekDto schedule) {
        ScheduleEntity scheduleToUpdate = findById(accountId, scheduleId)
                .orElseThrow(() -> new NoSuchElementException("Could not find schedule with id " + scheduleId));
        MemberEntity member = memberRepository.findById(schedule.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("Could not find member with id " + schedule.getMemberId()));

        List<ScheduleForDayDto> scheduleForDayDtos = new ArrayList<>();
        for (ScheduleForDayDto scheduleForDay : schedule.getSchedule()) {
            ScheduleForDayDto scheduleForDayDto = new ScheduleForDayDto();
            scheduleForDayDto.setDate(scheduleForDay.getDate());
            addMeal(scheduleForDay.getBreakfast(), BREAKFAST, member.getId(), scheduleForDayDto);
            addMeal(scheduleForDay.getSecondBreakfast(), SECOND_BREAKFAST, member.getId(), scheduleForDayDto);
            addMeal(scheduleForDay.getLunch(), LUNCH, member.getId(), scheduleForDayDto);
            addMeal(scheduleForDay.getDinner(), DINNER, member.getId(), scheduleForDayDto);
            addMeal(scheduleForDay.getSupper(), SUPPER, member.getId(), scheduleForDayDto);
            scheduleForDayDtos.add(scheduleForDayDto);
        }

        scheduleToUpdate.setUpdatedAt(new Date());
        scheduleToUpdate.setMemberId(schedule.getMemberId());
        scheduleToUpdate.setSchedule(ScheduleDtoMapper.convertToBytes(scheduleForDayDtos));
        return scheduleToUpdate;
    }

    @Transactional
    public List<ScheduleEntity> findAll(Long accountId) {
        return scheduleRepository.findAll(accountId);
    }

    @Transactional
    public Optional<ScheduleEntity> findById(Long accountId, Long scheduleId) {
        return scheduleRepository.findById(accountId, scheduleId);
    }

    @Transactional
    public Optional<ScheduleEntity> findByMember(Long accountId, Long memberId) {
        Pageable topOne = PageRequest.of(0, 1);
        return scheduleRepository.findByMember(accountId, memberId, topOne).stream().findFirst();
    }

    @Transactional
    public void deleteById(Long accountId, Long scheduleId) {
        ScheduleEntity schedule = findById(accountId, scheduleId)
                .orElseThrow(() -> new NoSuchElementException("Could not find schedule with id " + scheduleId));
        scheduleRepository.deleteById(scheduleId);
    }

    private Optional<ScheduleForWeekDto> getScheduleForWeekDto(Optional<ScheduleEntity> schedule) {
        return schedule.map(ScheduleDtoMapper::mapToScheduleDto);
    }
}
