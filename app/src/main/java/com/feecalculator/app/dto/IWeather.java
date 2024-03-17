package com.feecalculator.app.dto;

import com.feecalculator.app.enums.VehicleEnum;
import com.feecalculator.app.enums.WeatherEnum;

public interface IWeather {

    /**
     * WeatherEnum which specifies what type of weather this is.
     */
    WeatherEnum weatherType = null;

    /**
     * Takes in Double argument and returns
     * if the argument is in this weather range.
     * @param val Value that needs to be checked.
     * @return true if the argument is in the weather range, otherwise false.
     */
    boolean isThis(Double val);

    /**
     * Takes in VehicleEnum and returns the fee for this vehicle in this weather.
     * @param vehicle Given vehicle.
     * @return The fee in this weather.
     */
    Double getFee(VehicleEnum vehicle);

    /**
     * Takes in VehicleEnum vehicle and Double fee and sets the fee
     * according to the arguments.
     * @param vehicle VehicleEnum of vehicle.
     * @param fee Desired fee for vehicle.
     */
    void setFee(VehicleEnum vehicle, Double fee);

    /**
     * Takes in VehicleEnum vehicle and sets its fee to 0.0.
     * @param vehicle VehicleEnum of vehicle.
     */
    void removeFee(VehicleEnum vehicle);

    /**
     * Double matrix which has fees for each vehicle based on
     * VehicleEnums count.
     */
    double[] fees = new double[VehicleEnum.values().length];

    /**
     * Takes in VehicleEnum vehicle and returns if this vehicle
     * is allowed to drive in this weather condition.
     * @param vehicle VehicleEnum of vehicle.
     * @return If vehicle is allowed to drive in this weather condition.
     */
    boolean isNotAllowed(VehicleEnum vehicle);

    /**
     * Takes in VehicleEnum vehicle and allows it to drive in this
     * weather condition.
     * @param vehicle VehicleEnum of vehicle.
     */
    void allow(VehicleEnum vehicle);

    /**
     * Takes in VehicleEnum vehicle and disallows it to drive in this
     * weather condition.
     * @param vehicle VehicleEnum of vehicle.
     */
    void disallow(VehicleEnum vehicle);

    /**
     * Boolean matrix to show if a vehicle is allowed to drive in this
     * weather.
     */
    boolean[] notAllowedVehicles = new boolean[VehicleEnum.values().length];


}
