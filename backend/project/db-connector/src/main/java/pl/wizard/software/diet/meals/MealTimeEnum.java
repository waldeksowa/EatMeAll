package pl.wizard.software.diet.meals;

import java.util.Map;

public enum MealTimeEnum {
    FAKE("fake"),
    BREAKFAST("breakfast"),
    SECOND_BREAKFAST("second_breakfast"),
    LUNCH("lunch"),
    DINNER("dinner"),
    SUPPER("supper");

    private final String name;
    private static Map<String, MealTimeEnum> nameToValue;

    MealTimeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static MealTimeEnum formName(String name) {
        return nameToValue.get(name);
    }
}
