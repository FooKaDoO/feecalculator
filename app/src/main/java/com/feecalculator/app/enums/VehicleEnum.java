package com.feecalculator.app.enums;

public enum VehicleEnum {

    CAR(2),
    SCOOTER(1),
    BIKE(0);

    public final Integer label;

    VehicleEnum(Integer label) {
        this.label = label;
    }

    /**
     * Returns VehicleEnum from vehicle name. <br>
     * @param name Name of vehicle.
     * @return VehicleEnum with given name.
     */
    public static VehicleEnum fromName(String name) {
        if (name == null)
            return null;
        return switch (name.toLowerCase()) {
            case "car" -> VehicleEnum.CAR;
            case "scooter" -> VehicleEnum.SCOOTER;
            case "bike" -> VehicleEnum.BIKE;
            default -> null;
        };
    }
}
