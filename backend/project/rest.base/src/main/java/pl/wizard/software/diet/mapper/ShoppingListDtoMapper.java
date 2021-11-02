package pl.wizard.software.diet.mapper;

import pl.wizard.software.diet.dto.ShoppingListDto;
import pl.wizard.software.diet.dto.ShoppingListItemDto;
import pl.wizard.software.diet.shoppingList.ShoppingListEntity;
import pl.wizard.software.diet.shoppingList.ShoppingListItemEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ShoppingListDtoMapper {
    private ShoppingListDtoMapper() {
    }

    public static ShoppingListDto mapToShoppingListDto(ShoppingListEntity shoppingListEntity) {
        return ShoppingListDto.builder()
                .id(shoppingListEntity.getId())
                .createdAt(shoppingListEntity.getCreatedAt())
                .updatedAt(shoppingListEntity.getUpdatedAt())
                .version(shoppingListEntity.getVersion())
                .accountId(shoppingListEntity.getAccount().getId())
                .shoppingListDate(shoppingListEntity.getShoppingListDate())
                .items(mapToShoppingListItemDtos(shoppingListEntity.getItems()))
                .build();
    }

    private static List<ShoppingListItemDto> mapToShoppingListItemDtos(List<ShoppingListItemEntity> items) {
        return items.stream()
                .map(shoppingListItemEntity -> mapToShoppingListItemDto(shoppingListItemEntity))
                .collect(Collectors.toList());
    }

    private static ShoppingListItemDto mapToShoppingListItemDto(ShoppingListItemEntity shoppingListItemEntity) {
        return ShoppingListItemDto.builder()
                .id(shoppingListItemEntity.getId())
                .createdAt(shoppingListItemEntity.getCreatedAt())
                .updatedAt(shoppingListItemEntity.getUpdatedAt())
                .version(shoppingListItemEntity.getVersion())
                .productId(shoppingListItemEntity.getProduct().getId())
                .productName(shoppingListItemEntity.getProduct().getName())
                .amount(shoppingListItemEntity.getAmount())
                .specialAmount(shoppingListItemEntity.getSpecialAmount())
                .isBuyed(shoppingListItemEntity.isBuyed())
                .build();
    }

}
