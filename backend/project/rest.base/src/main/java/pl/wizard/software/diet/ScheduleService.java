package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wizard.software.diet.meals.MealEntity;
import pl.wizard.software.diet.meals.MealTimeEnum;
import pl.wizard.software.diet.meals.MemberMealDao;
import pl.wizard.software.diet.meals.MemberMealEntity;
import pl.wizard.software.diet.members.MemberDao;
import pl.wizard.software.diet.members.MemberEntity;
import pl.wizard.software.diet.schedules.ScheduleDao;
import pl.wizard.software.diet.schedules.ScheduleEntity;
import pl.wizard.software.dto.CreateScheduleDto;
import pl.wizard.software.dto.CreateScheduleForDayDto;
import pl.wizard.software.dto.ScheduleForDayDto;
import pl.wizard.software.dto.ScheduleForWeekDto;
import pl.wizard.software.util.ByteConverter;

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
    private final MemberMealDao memberMealRepository;

    public ScheduleForWeekDto getScheduleByMealTime() {
        ScheduleForWeekDto schedule = initializeScheduleForWeekDto();
        for (int i = 1; i < MealTimeEnum.values().length; i++) {
            List<MealEntity> meals = mealService.findRandomByMealTime(MealTimeEnum.values()[i].ordinal(), DAYS_IN_WEEK);
            for (DayOfWeek day : DayOfWeek.values()) {
                Optional<MealEntity> meal = meals.stream().findFirst();
                if (meal.isPresent()) {
                    schedule.getSchedule().get(day.ordinal()).add(meal.get(), MealTimeEnum.values()[i]);
                    meals.remove(meal.get());
                }
            }
        }
        return schedule;
    }

    public ScheduleForWeekDto getScheduleByMealTime(Long memberId) {
        ScheduleForWeekDto schedule = initializeScheduleForWeekDto();
        for (int i = 1; i < MealTimeEnum.values().length; i++) {
            List<MealEntity> meals = mealService.findRandomByMealTime(MealTimeEnum.values()[i].ordinal(), DAYS_IN_WEEK);
            for (DayOfWeek day : DayOfWeek.values()) {
                Optional<MealEntity> meal = meals.stream().findFirst();
                if (meal.isPresent()) {
                    Optional<MemberMealEntity> memberMeal = memberMealRepository.findByMemberAndParentMeal(memberId, meal.get().getId());
                    if (memberMeal.isPresent()) {
                        schedule.getSchedule().get(day.ordinal()).add(memberMeal.get(), MealTimeEnum.values()[i]);
                    } else {
                        schedule.getSchedule().get(day.ordinal()).add(meal.get(), MealTimeEnum.values()[i]);
                    }
                    meals.remove(meal.get());
                }
            }
        }
        return schedule;
    }

    private ScheduleForWeekDto initializeScheduleForWeekDto() {
        ScheduleForWeekDto schedule = new ScheduleForWeekDto();
        LocalDate nextWeekMonday = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().ordinal()).plusDays(DAYS_IN_WEEK);
        for (DayOfWeek day : DayOfWeek.values()) {
            ScheduleForDayDto scheduleForDayDto = new ScheduleForDayDto();
            scheduleForDayDto.setDate(nextWeekMonday.plusDays(day.ordinal()));
            schedule.getSchedule().add(scheduleForDayDto);
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
            addMeal(createScheduleForDayDto.getBreakfast(), BREAKFAST, scheduleForDayDto);
            addMeal(createScheduleForDayDto.getSecondBreakfast(), SECOND_BREAKFAST, scheduleForDayDto);
            addMeal(createScheduleForDayDto.getLunch(), LUNCH, scheduleForDayDto);
            addMeal(createScheduleForDayDto.getDinner(), DINNER, scheduleForDayDto);
            addMeal(createScheduleForDayDto.getSupper(), SUPPER, scheduleForDayDto);
            scheduleForDayDtos.add(scheduleForDayDto);
        }

        ScheduleEntity scheduleEntity = ScheduleEntity.builder()
                .member(member)
                .scheduleDate(scheduleDate)
                .schedule(ByteConverter.convertToBytes(scheduleForDayDtos))
                .build();

        return scheduleRepository.save(scheduleEntity);
    }

    private void addMeal(Long mealId, MealTimeEnum mealTime, ScheduleForDayDto scheduleForDayDto) {
        MealEntity mealEntity = mealService.findById(mealId);
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
            addMeal(scheduleForDay.getBreakfast(), BREAKFAST, scheduleForDayDto);
            addMeal(scheduleForDay.getSecondBreakfast(), SECOND_BREAKFAST, scheduleForDayDto);
            addMeal(scheduleForDay.getLunch(), LUNCH, scheduleForDayDto);
            addMeal(scheduleForDay.getDinner(), DINNER, scheduleForDayDto);
            addMeal(scheduleForDay.getSupper(), SUPPER, scheduleForDayDto);
            scheduleForDayDtos.add(scheduleForDayDto);
        }

        scheduleToUpdate.setUpdatedAt(new Date());
        scheduleToUpdate.setMember(member);
        scheduleToUpdate.setSchedule(ByteConverter.convertToBytes(scheduleForDayDtos));
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
}
