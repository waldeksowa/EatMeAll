package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wizard.software.diet.dto.ProductWithAmountDto;
import pl.wizard.software.diet.dto.ScheduleForDayDto;
import pl.wizard.software.diet.dto.ScheduleForWeekDto;
import pl.wizard.software.diet.mapper.ProductDtoMapper;
import pl.wizard.software.diet.meals.MealDao;
import pl.wizard.software.diet.meals.MealEntity;
import pl.wizard.software.diet.meals.MealProductEntity;
import pl.wizard.software.diet.products.ProductEntity.ProductTypeEnum;
import pl.wizard.software.diet.shoppingList.ShoppingListDao;
import pl.wizard.software.diet.shoppingList.ShoppingListEntity;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class  ShoppingListService {

    private final MealDao mealRepository;
    private final ShoppingListDao shoppingListRepository;
    private final ScheduleService scheduleService;

    HashMap<ProductTypeEnum, List<ProductWithAmountDto>> getShoppingList(List<Long> ids) {
        HashMap<ProductTypeEnum, List<ProductWithAmountDto>> shoppingList = new HashMap<>();
        List<MealProductEntity> mealProducts = new ArrayList<>();
        for (Long id : ids) {
            Optional<MealEntity> meal = mealRepository.findById(id);
            if (!meal.isPresent()) {
                log.error("Meal with id = " + id + " does not exists");
            } else {
                meal.get().getProducts()
                        .forEach(mealProductEntity -> mealProducts.add(mealProductEntity));
            }
        }
        List<MealProductEntity> uniqueProducts = makeProductsUnique(mealProducts);
        prepareShoppingList(shoppingList, uniqueProducts);

        return shoppingList;
    }

    public ShoppingListEntity save(ShoppingListEntity shoppingList) {
        return shoppingListRepository.save(shoppingList);
    }

    public Optional<ShoppingListEntity> findById(Long accountId, Long shoppingListId) {
        return shoppingListRepository.findById(accountId, shoppingListId);
    }

    public void deleteById(Long shoppingListId) {
        shoppingListRepository.deleteById(shoppingListId);
    }

    public HashMap<ProductTypeEnum, List<ProductWithAmountDto>> getByMemberAndDay(List<Long> members, List<DayOfWeek> days, Long accountId) {
        List<Long> mealIds = new ArrayList<>();
        for (Long memberId : members) {
            Optional<ScheduleForWeekDto> schedule = scheduleService.findByMember(accountId, memberId);
            if (!schedule.isPresent()) {
                log.error("Schedule for member with id = " + memberId + " does not exists");
            } else {
                for (ScheduleForDayDto mealsForDay : schedule.get().getSchedule()) {
                    if (days.contains(mapToDayOfWeek(mealsForDay.getDate()))) {
                        mealIds.addAll(getMealIds(mealsForDay));
                    }
                }
            }
        }
        return getShoppingList(mealIds);
    }

    private DayOfWeek mapToDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = (cal.get(Calendar.DAY_OF_WEEK) + 6) % 7;
        return (day == 0) ? DayOfWeek.SUNDAY : DayOfWeek.of(day);
    }

    private List<Long> getMealIds(ScheduleForDayDto mealsForDay) {
        List<Long> ids = new ArrayList<>();
        if (mealsForDay.getBreakfast() != null) {
            ids.add(mealsForDay.getBreakfast());
        }
        if (mealsForDay.getSecondBreakfast() != null) {
            ids.add(mealsForDay.getSecondBreakfast());
        }
        if (mealsForDay.getLunch() != null) {
            ids.add(mealsForDay.getLunch());
        }
        if (mealsForDay.getSupper() != null) {
            ids.add(mealsForDay.getSupper());
        }
        if (mealsForDay.getDinner() != null) {
            ids.add(mealsForDay.getDinner());
        }
        return ids;
    }

    private List<MealProductEntity> makeProductsUnique(List<MealProductEntity> mealProducts) {
        Map<Long, MealProductEntity> mealProductsMap = new HashMap<>();
        for (MealProductEntity mealProduct : mealProducts) {
            MealProductEntity current = mealProductsMap.get(mealProduct.getProduct().getId());
            if (current == null) {
                mealProductsMap.put(mealProduct.getProduct().getId(), copyOf(mealProduct));
            } else {
                current.setAmount(current.getAmount() + mealProduct.getAmount());
            }
        }
        return List.copyOf(mealProductsMap.values());
    }

    private void prepareShoppingList(HashMap<ProductTypeEnum, List<ProductWithAmountDto>> shoppingList, List<MealProductEntity> uniqueProducts) {
        Set<ProductTypeEnum> types = uniqueProducts.stream()
                .map(mealProductEntity -> mealProductEntity.getProduct().getProductType())
                .collect(Collectors.toSet());
        types.forEach(type -> shoppingList.put(type, new ArrayList<>()));
        for (MealProductEntity uniqueProduct : uniqueProducts) {
            shoppingList.get(uniqueProduct.getProduct().getProductType()).add(ProductDtoMapper.mapToProductWithAmountDto(uniqueProduct));
        }
    }

    private MealProductEntity copyOf(MealProductEntity mealProductEntity) {
        MealProductEntity result = new MealProductEntity();
        result.setId(mealProductEntity.getId());
        result.setCreatedAt(mealProductEntity.getCreatedAt());
        result.setUpdatedAt(mealProductEntity.getUpdatedAt());
        result.setVersion(mealProductEntity.getVersion());
        result.setProduct(mealProductEntity.getProduct());
        result.setAmount(mealProductEntity.getAmount());
        result.setSpecialAmount(mealProductEntity.getSpecialAmount());
        result.setSpecialAmountUnit(mealProductEntity.getSpecialAmountUnit());
        return result;
    }
}
