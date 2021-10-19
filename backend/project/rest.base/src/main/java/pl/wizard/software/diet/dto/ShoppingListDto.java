package pl.wizard.software.diet.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListDto {
    private Long accountId;
    private Date shoppingListDate;
    private List<ShoppingListItemDto> items;
}
