package pl.wizard.software.diet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalculateProductAmountFactory {

    private static final Map<Type, CalculateProductAmountIf> calculatorCache = new HashMap<>();

    @Autowired
    private CalculateProductAmountFactory(List<CalculateProductAmountIf> calculators) {
        for (CalculateProductAmountIf calculator : calculators) {
            calculatorCache.put(calculator.getType(), calculator);
        }
    }

    public static CalculateProductAmountIf getCalculator(CalculateProductAmountFactory.Type type) {
        CalculateProductAmountIf calculator = calculatorCache.get(type);
        if (calculator == null) throw new RuntimeException("Unknown calculator type " + type.toString());
        return calculator;
    }

    public enum Type {
        DEFAULT
    }
}
