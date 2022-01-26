package pl.wizard.software.email;

import lombok.Builder;

@Builder
public class ShoppingListRow {
    private String productName;
    private int amount;
    private int specialAmount;
    private boolean isBuyed;
}
