package pl.wizard.software.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateShoppingListItemDto {
    private Long productId;
    private int amount;
    private int specialAmount;
    private boolean isBuyed;
}
