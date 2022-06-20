package pl.wizard.software.email;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ShoppingListFileData {
    private Date shoppingListDate;
    private List<ShoppingListRow> rows;
}
