package pl.wizard.software.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListDto {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private int version;
    private Long accountId;
    private Date shoppingListDate;
    private List<ShoppingListItemDto> items;
}
