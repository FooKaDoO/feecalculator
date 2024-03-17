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
     * Used for mapping data without needing to do String comparisons. <br>
     * If WMO code is null, returns null.
     * @param WMO Station's WMO code.
     * @return Enum of respective station or null if station is not "Tallinn-Harku", "Tartu-Tõravere" or "Pärnu".
     */
    public static StationEnum fromWMOCode(Integer WMO) {
        if (WMO == null)
            return null;
        return switch (WMO) {
            case 26038 -> StationEnum.TALLINN;
            case 26242 -> StationEnum.TARTU;
            case 41803 -> StationEnum.PÄRNU;
            default -> null;
        };
    }

    /**
     * Returns StationEnum by simplified name. <br>
     * Analogous to fromWMOCode(). <br>
     * <br>
     * Used for HTTP requests. That is why
     * the name is simplified.
     * @param name Name of station.
     * @return StationEnum with given name.
     */
    public static StationEnum fromName(String name) {
        if (name == null)
            return null;
        return switch (name.toLowerCase()) {
            case "tallinn" -> StationEnum.TALLINN;
            case "tartu" -> StationEnum.TARTU;
            case "pärnu" -> StationEnum.PÄRNU;
            default -> null;
        };
    }
}