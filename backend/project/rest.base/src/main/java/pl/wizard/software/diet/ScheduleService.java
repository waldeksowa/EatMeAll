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
import pl.wizard.software.diet.members.MemberDao;
import pl.wizard.software.diet.members.MemberEntity;
import pl.wizard.software.diet.schedules.ScheduleDao;
import pl.wizard.software.diet.schedules.ScheduleEntity;
import pl.wizard.software.dto.*;
import pl.wizard.software.mapper.ScheduleDtoMapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
    private final MemberDao memberRepository;
    private final CalculateProductAmountFactory calculateProductAmountFactory;

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
            addMeal(createScheduleForDayDto.getBreakfast(), BREAKFAST, member.getRecommendedCalories(), scheduleForDayDto);
            addMeal(createScheduleForDayDto.getSecondBreakfast(), SECOND_BREAKFAST, member.getRecommendedCalories(), scheduleForDayDto);
            addMeal(createScheduleForDayDto.getLunch(), LUNCH, member.getRecommendedCalories(), scheduleForDayDto);
            addMeal(createScheduleForDayDto.getDinner(), DINNER, member.getRecommendedCalories(), scheduleForDayDto);
            addMeal(createScheduleForDayDto.getSupper(), SUPPER, member.getRecommendedCalories(), scheduleForDayDto);
            scheduleForDayDtos.add(scheduleForDayDto);
        }

        ScheduleEntity scheduleEntity = ScheduleEntity.builder()
                .memberId(schedule.getMemberId())
                .scheduleDate(scheduleDate)
                .schedule(ScheduleDtoMapper.convertToBytes(scheduleForDayDtos))
                .build();

        return ScheduleDtoMapper.mapToScheduleDto(scheduleRepository.save(scheduleEntity));
    }

    private void addMeal(Long mealId, MealTimeEnum mealTime, Double memberCalories, ScheduleForDayDto scheduleForDayDto) {
        MealEntity mealEntity = mealRepository.findById(mealId)
                .orElseThrow(() -> new NoSuchElementException("Could not find meal with id " + mealId));
        customizeMealEntity(memberCalories, mealEntity);
        scheduleForDayDto.add(mealEntity, mealTime);
    }

    private void customizeMealEntity(Double memberCalories, MealEntity mealEntity) {
        CalculateProductAmountIf amountCalculator = calculateProductAmountFactory.createCalculator();
        mealEntity.getProducts().forEach(
                mealProduct -> mealProduct.setAmount(amountCalculator.calculateProductAmount(mealProduct.getAmount(), mealEntity.getCalorific(), memberCalories))
        );
        recalculateMealMacros(mealEntity);
    }

    private void recalculateMealMacros(MealEntity mealEntity) {
        mealEntity.setCalorific(mealEntity.getProducts().stream()
                .mapToDouble(mealProduct -> (mealProduct.getAmount() / 100.0) * mealProduct.getProduct().getCalorific()).sum());
        mealEntity.setProtein(mealEntity.getProducts().stream()
                .mapToDouble(mealProduct -> (mealProduct.getAmount() / 100.0) * mealProduct.getProduct().getProtein()).sum());
        mealEntity.setFat(mealEntity.getProducts().stream()
                .mapToDouble(mealProduct -> (mealProduct.getAmount() / 100.0) * mealProduct.getProduct().getFat()).sum());
        mealEntity.setCarbohydrates(mealEntity.getProducts().stream()
                .mapToDouble(mealProduct -> (mealProduct.getAmount() / 100.0) * mealProduct.getProduct().getCarbohydrates()).sum());
        mealEntity.setRoughage(mealEntity.getProducts().stream()
                .mapToDouble(mealProduct -> (mealProduct.getAmount() / 100.0) * mealProduct.getProduct().getRoughage()).sum());
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

    @Transactional
    public ScheduleForWeekDto save(Long accountId, Long scheduleId, ScheduleForWeekDto schedule) {
        ScheduleForWeekDto scheduleToUpdate = findById(accountId, scheduleId)
                .orElseThrow(() -> new NoSuchElementException("Could not find schedule with id " + scheduleId));
        ScheduleEntity scheduleEntity = ScheduleDtoMapper.mapToScheduleEntity(schedule);
        return ScheduleDtoMapper.mapToScheduleDto(scheduleRepository.save(scheduleEntity));
    }

    @Transactional
    public void deleteById(Long accountId, Long scheduleId) {
        ScheduleForWeekDto schedule = findById(accountId, scheduleId)
                .orElseThrow(() -> new NoSuchElementException("Could not find schedule with id " + scheduleId));
        scheduleRepository.deleteById(scheduleId);
    }

    private Optional<ScheduleForWeekDto> getScheduleForWeekDto(Optional<ScheduleEntity> schedule) {
        return schedule.map(ScheduleDtoMapper::mapToScheduleDto);
    }

    public MealEntity customizeMeal(MemberDto member, MealEntity meal) {
        customizeMealEntity(member.getRecommendedCalories(), meal);
        return meal;
    }
}
