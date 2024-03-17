package com.feecalculator.app.dto.IWeatherTypes;

import com.feecalculator.app.dto.IWeather;
import com.feecalculator.app.enums.WeatherEnum;

public interface IWindSpeed extends IWeather {
    WeatherEnum weatherType = WeatherEnum.WINDSPEED;
}
