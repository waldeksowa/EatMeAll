package pl.wizard.software.email;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShoppingListRow {
    private String productName;
    private String amount;
    private String specialAmount;
    private String bought;
}
