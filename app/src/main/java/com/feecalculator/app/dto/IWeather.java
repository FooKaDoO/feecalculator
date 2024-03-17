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
    default Double getFee(VehicleEnum vehicle) {
        return fees[vehicle.label];
    };

    /**
     * Takes in VehicleEnum vehicle and Double fee and sets the fee
     * according to the arguments.
     * @param vehicle VehicleEnum of vehicle.
     * @param fee Desired fee for vehicle.
     */
    default void setFee(VehicleEnum vehicle, Double fee) {
        fees[vehicle.label] = fee;
    }

    /**
     * Takes in VehicleEnum vehicle and sets its fee to 0.0.
     * @param vehicle VehicleEnum of vehicle.
     */
    default void removeFee(VehicleEnum vehicle) {
        fees[vehicle.label] = 0.0;
    }

    /**
     * Double matrix which has fees for each vehicle based on
     * VehicleEnums count.
     */
    Double[] fees = new Double[VehicleEnum.values().length];

    /**
     * Takes in VehicleEnum vehicle and returns if this vehicle
     * is allowed to drive in this weather condition.
     * @param vehicle VehicleEnum of vehicle.
     * @return If vehicle is allowed to drive in this weather condition.
     */
    default boolean isAllowed(VehicleEnum vehicle) {
        return notAllowedVehicles[vehicle.label];
    }

    /**
     * Takes in VehicleEnum vehicle and allows it to drive in this
     * weather condition.
     * @param vehicle VehicleEnum of vehicle.
     */
    default void allow(VehicleEnum vehicle) {
        notAllowedVehicles[vehicle.label] = false;
    }

    /**
     * Takes in VehicleEnum vehicle and disallows it to drive in this
     * weather condition.
     * @param vehicle VehicleEnum of vehicle.
     */
    default void disallow(VehicleEnum vehicle) {
        notAllowedVehicles[vehicle.label] = true;
    }

    /**
     * Boolean matrix to show if a vehicle is allowed to drive in this
     * weather.
     */
    boolean[] notAllowedVehicles = new boolean[VehicleEnum.values().length];


}
