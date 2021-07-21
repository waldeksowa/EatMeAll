package pl.wizard.software.diet.meals;

public enum SpecialAmountEnum {

    SLICE("kromka", "kromki", "kromek"),
    HANDFUL("garść", "garście", "garści"),
    PINCH("szczypta", "szczypty", "szczypt"),
    PACKAGE("opakowanie", "opakowania", "opakowań"),
    GLASS("szklanka", "szklanki", "szklanek"),
    SPOON("łyżka", "łyżki", "łyżek"),
    TEASPOON("łyżeczka", "łyżeczki", "łyżeczek"),
    CUBE("kostka", "kostki", "kostek");

    private final String singular;
    private final String smallPlural;
    private final String bigPlural;

    private SpecialAmountEnum(String aSingular, String aSmallPlural, String aBigPlural) {
        singular = aSingular;
        smallPlural = aSmallPlural;
        bigPlural = aBigPlural;
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
