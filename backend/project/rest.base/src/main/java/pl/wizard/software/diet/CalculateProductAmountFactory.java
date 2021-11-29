package pl.wizard.software.diet;

import org.springframework.stereotype.Service;

@Service
public class CalculateProductAmountFactory {
    public static CalculateProductAmountIf createCalculator() {
        return new DefaultCalculateProductAmount();
    }
}
