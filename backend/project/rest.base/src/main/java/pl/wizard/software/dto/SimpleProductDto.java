package pl.wizard.software.dto;

import lombok.Getter;
import lombok.Setter;
import pl.wizard.software.diet.meals.SpecialAmountEnum;

@Getter
@Setter
public class SimpleProductDto {
    private long id;
    private int amount;
    private int specialAmount;
    private SpecialAmountEnum specialAmountUnit;
}
