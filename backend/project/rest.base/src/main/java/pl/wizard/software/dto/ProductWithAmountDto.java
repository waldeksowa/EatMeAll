package pl.wizard.software.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductWithAmountDto {
    private long id;
    private String name;
    private int amount;
    private String specialAmount;
}
