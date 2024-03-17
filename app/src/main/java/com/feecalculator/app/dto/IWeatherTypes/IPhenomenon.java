package com.feecalculator.app.dto.IWeatherTypes;

import com.feecalculator.app.dto.IWeather;
import com.feecalculator.app.enums.PhenomenonEnum;
import com.feecalculator.app.enums.WeatherEnum;

public interface IPhenomenon extends IWeather {
    WeatherEnum weatherType = WeatherEnum.PHENOMENON;
    PhenomenonEnum phenomenon = null;
    @Override
    default boolean isThis(Double val) {
        if (phenomenon == null)
            return false;
        return val.equals(phenomenon.label);
    }
}
