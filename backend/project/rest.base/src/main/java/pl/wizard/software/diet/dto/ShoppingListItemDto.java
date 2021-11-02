package pl.wizard.software.diet.dto;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListItemDto {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private int version;
    private Long productId;
    private String productName;
    private int amount;
    private int specialAmount;
    private boolean isBuyed;
}
