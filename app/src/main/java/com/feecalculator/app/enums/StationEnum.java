package com.feecalculator.app.enums;

/**
 * https://www.baeldung.com/java-enum-values
 */
public enum StationEnum {
    TALLINN("Tallinn-Harku"),
    TARTU("Tartu-Tõravere"),
    PÄRNU("Pärnu");

    public final String label;

    StationEnum(String label) {
        this.label = label;
    }

    /**
     * Return StationEnum by WMO code.<br>
     * Used for mapping data without needing to do String comparisons.
     * @param WMO Station's WMO code.
     * @return Enum of respective station or null if station is not "Tallinn-Harku", "Tartu-Tõravere" or "Pärnu".
     */
    public static StationEnum fromWMOCode(Integer WMO) {
        return switch (WMO) {
            case 26038 -> StationEnum.TALLINN;
            case 26242 -> StationEnum.TARTU;
            case 41803 -> StationEnum.PÄRNU;
            default -> null;
        };
    }
}