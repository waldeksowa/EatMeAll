package pl.wizard.software.mapper;

import pl.wizard.software.dto.ShoppingListDto;
import pl.wizard.software.dto.ShoppingListItemDto;
import pl.wizard.software.email.ShoppingListFileData;
import pl.wizard.software.email.ShoppingListRow;

import java.util.List;
import java.util.stream.Collectors;

public class ShoppingListFileDataMapper {

    public static ShoppingListFileData mapToFileData(ShoppingListDto shoppingList) {
        ShoppingListFileData shoppingListFileData = new ShoppingListFileData();
        shoppingListFileData.setShoppingListDate(shoppingList.getShoppingListDate());
        shoppingListFileData.setRows(mapToFileRows(shoppingList.getItems()));

        return shoppingListFileData;
    }

    private static List<ShoppingListRow> mapToFileRows(List<ShoppingListItemDto> items) {
        return items.stream()
                .map(item -> mapToFileRow(item))
                .collect(Collectors.toList());
    }

    private static ShoppingListRow mapToFileRow(ShoppingListItemDto item) {
        return ShoppingListRow.builder()
                .productName(item.getProductName())
                .amount(item.getAmount())
                .specialAmount(item.getSpecialAmount())
                .isBuyed(item.isBuyed())
                .build();
    }
}
