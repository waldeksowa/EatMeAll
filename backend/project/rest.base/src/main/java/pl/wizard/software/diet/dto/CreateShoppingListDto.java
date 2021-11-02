package pl.wizard.software.diet.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateShoppingListDto {
    private Long accountId;
    private Date shoppingListDate;
    private List<CreateShoppingListItemDto> items;
}
