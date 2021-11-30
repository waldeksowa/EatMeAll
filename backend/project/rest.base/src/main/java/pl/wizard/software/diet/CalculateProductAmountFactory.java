package pl.wizard.software.diet;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@NoArgsConstructor
public class CalculateProductAmountFactory {

    public CalculateProductAmountIf createCalculator() {
        return new DefaultCalculateProductAmount();
    }
}
