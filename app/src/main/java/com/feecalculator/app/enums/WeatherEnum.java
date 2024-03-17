package com.feecalculator.app.enums;

public enum WeatherEnum {
    TEMPERATURE(2),
    WINDSPEED(1),
    PHENOMENON(0);

    /**
     * Map a double value to the enums.
     * Then it is possible to easily check if
     * a phenomenon is in a weather range.
     */
    public final Integer label;

    WeatherEnum(Integer label) {
        this.label = label;
    }

    /**
     * Returns WeatherEnum based on weather type.
     * @param type Given weather type.
     * @return WeatherEnum with the given type or null
     * if weather type is not used.
     */
    public static WeatherEnum fromName(String type) {
        type = type.toLowerCase();

        if (type.contains("temp") || type.contains("atef"))
            return WeatherEnum.TEMPERATURE;
        if (type.contains("wind") || type.contains("speed") || type.contains("wsef"))
            return WeatherEnum.WINDSPEED;
        if (type.contains("phenomenon") || type.contains("wpef"))
            return WeatherEnum.PHENOMENON;
        return null;
    }
}
