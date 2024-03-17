package com.feecalculator.app.dto;

import com.feecalculator.app.enums.StationEnum;
import com.feecalculator.app.enums.VehicleEnum;

public interface IStation {
    /**
     * StationEnum which specifies which station this is.
     */
    StationEnum stationType = null;

    /**
     * Takes in VehicleEnum and returns the fee for this vehicle in this station.
     * @param vehicle Given vehicle.
     * @return The fee in this station.
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
}
