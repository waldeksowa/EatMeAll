package pl.wizard.software.diet.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductWithAmountDto {
    private long id;
    private String name;
    private int amount;
}
