package pl.wizard.software.diet.meals;

import java.util.HashMap;
import java.util.Map;

public enum SpecialAmountEnum {

    SLICE("kromka", "kromki", "kromek"),
    HANDFUL("garść", "garście", "garści"),
    PINCH("szczypta", "szczypty", "szczypt"),
    PACKAGE("opakowanie", "opakowania", "opakowań"),
    GLASS("szklanka", "szklanki", "szklanek"),
    SPOON("łyżka", "łyżki", "łyżek"),
    TEASPOON("łyżeczka", "łyżeczki", "łyżeczek"),
    CUBE("kostka", "kostki", "kostek");

    private static final Map<String, SpecialAmountEnum> BY_SINGULAR = new HashMap<>();
    private static final Map<String, SpecialAmountEnum> BY_SMALL_PLURAL = new HashMap<>();
    private static final Map<String, SpecialAmountEnum> BY_BIG_PLURAL = new HashMap<>();

    static {
        for (SpecialAmountEnum sa : values()) {
            BY_SINGULAR.put(sa.singular, sa);
            BY_SMALL_PLURAL.put(sa.smallPlural, sa);
            BY_BIG_PLURAL.put(sa.bigPlural, sa);
        }
    }

    private final String singular;
    private final String smallPlural;
    private final String bigPlural;

    private SpecialAmountEnum(String aSingular, String aSmallPlural, String aBigPlural) {
        singular = aSingular;
        smallPlural = aSmallPlural;
        bigPlural = aBigPlural;
    }

    public static SpecialAmountEnum valueOfSingular(String aSingular) {
        return BY_SINGULAR.get(aSingular);
    }

    public static SpecialAmountEnum valueOfSmallPlural(String aSmallPlural) {
        return BY_SMALL_PLURAL.get(aSmallPlural);
    }

    public static SpecialAmountEnum valueOfBigPlural(String aBigPlural) {
        return BY_BIG_PLURAL.get(aBigPlural);
    }

    public String nameByAmount(int aAmount) {
        switch (aAmount) {
            case 1:
                return this.singular;
            case 2:
            case 3:
            case 4:
                return this.smallPlural;
            default:
                return this.bigPlural;
        }
    }
}
