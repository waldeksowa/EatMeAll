package pl.wizard.software.diet.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListItemDto {
    private Long product;
    private int amount;
    private boolean isBuyed;
}
