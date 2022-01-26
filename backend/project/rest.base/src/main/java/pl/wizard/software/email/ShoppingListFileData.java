package pl.wizard.software.email;

import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
public class ShoppingListFileData {
    private Date shoppingListDate;
    private List<ShoppingListRow> rows;
}
