package com.feecalculator.app.enums;

public enum PhenomenonEnum {
    SNOW(5.0),
    SLEET(4.0),
    RAIN(3.0),
    GLAZE(2.0),
    HAIL(1.0),
    THUNDER(0.0);

    /**
     * Map a double value to the enums.
     * Then it is possible to easily check if
     * a phenomenon is in a weather range.
     */
    public final Double label;

    PhenomenonEnum(Double label) {
        this.label = label;
    }


    /**
     * Returns PhenomenonEnum based on phenomenon name.<br>
     * Since shower is also connected to rain,
     * then if contains shower, also return rain.<br>
     * There is also a value snow shower,
     * but since it contains "snow",
     * it will be returned before the code reaches "shower".
     * @param name Given phenomenon name.
     * @return PhenomenonEnum with the given name or null
     * if phenomenon is not used.
     */
    public static PhenomenonEnum fromName(String name) {
        name = name.toLowerCase();

        if (name.contains("snow"))
            return PhenomenonEnum.SNOW;
        if (name.contains("sleet"))
            return PhenomenonEnum.SLEET;
        if (name.contains("rain") ||
            name.contains("shower"))
            return PhenomenonEnum.RAIN;
        if (name.contains("glaze"))
            return PhenomenonEnum.GLAZE;
        if (name.contains("hail"))
            return PhenomenonEnum.HAIL;
        if (name.contains("thunder"))
            return PhenomenonEnum.THUNDER;
        return null;
    }
}
