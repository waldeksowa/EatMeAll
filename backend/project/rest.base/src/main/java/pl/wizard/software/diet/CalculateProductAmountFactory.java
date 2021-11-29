package pl.wizard.software.diet;

public class CalculateProductAmountFactory {
    public enum Type {
        DEFAULT
    }

    public static CalculateProductAmountIf create(CalculateProductAmountFactory.Type type) {
        return new DefaultCalculateProductAmount();
    }
}
