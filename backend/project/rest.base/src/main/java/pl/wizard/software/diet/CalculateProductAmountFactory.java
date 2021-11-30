package pl.wizard.software.diet;

import org.springframework.stereotype.Service;

@Service
public class CalculateProductAmountFactory {

    public CalculateProductAmountIf createCalculator() {
        return new DefaultCalculateProductAmount();
    }
}
