package com.feecalculator.app.dto.IWeatherTypes;

import com.feecalculator.app.dto.IWeather;
import com.feecalculator.app.enums.WeatherEnum;


public interface ITemperature extends IWeather {
    WeatherEnum weatherType = WeatherEnum.TEMPERATURE;
}
