package com.feecalculator.app.repository;

import com.feecalculator.app.dto.IStation;
import com.feecalculator.app.dto.IWeatherTypes.IPhenomenon;
import com.feecalculator.app.dto.IWeatherTypes.ITemperature;
import com.feecalculator.app.dto.IWeatherTypes.IWindSpeed;
import com.feecalculator.app.enums.PhenomenonEnum;
import com.feecalculator.app.enums.StationEnum;
import com.feecalculator.app.enums.VehicleEnum;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeeRepository {

    /**
     * Hashmaps, since they use a single value to determine their type.
     */
    HashMap<StationEnum, IStation> stations = initStation();
    HashMap<PhenomenonEnum, IPhenomenon> phenomenons = initPhenomenon();
    /**
     * Lists, since they use a range to determine their type.
     */
    List<ITemperature> temperatures = initTemperature();
    List<IWindSpeed> windSpeeds = initWindSpeed();


    /**
     * Method to initialize the hashmap of station with initial fees.
     * @return Hashmap of stations.
     */
    private HashMap<StationEnum, IStation> initStation() {
        HashMap<StationEnum, IStation> stations = new HashMap<>();

        // For each possible station value, create an IStation.
        for (StationEnum station : StationEnum.values()) {
            stations.put(station, new IStation() {
                final StationEnum stationEnum = station;
            });
        }
        /**
         * Set initial fees for stations.
         */
        IStation tallinn = stations.get(StationEnum.TALLINN);
        tallinn.setFee(VehicleEnum.CAR, 4.0);
        tallinn.setFee(VehicleEnum.SCOOTER, 3.5);
        tallinn.setFee(VehicleEnum.BIKE, 3.0);

        IStation tartu = stations.get(StationEnum.TARTU);
        tartu.setFee(VehicleEnum.CAR, 3.5);
        tartu.setFee(VehicleEnum.SCOOTER, 3.0);
        tartu.setFee(VehicleEnum.BIKE, 2.5);

        IStation pärnu = stations.get(StationEnum.PÄRNU);
        pärnu.setFee(VehicleEnum.CAR, 3.0);
        pärnu.setFee(VehicleEnum.SCOOTER, 2.5);
        pärnu.setFee(VehicleEnum.BIKE, 2.0);

        return stations;
    }

    /**
     * Method to initialize the hashmap of phenomenons with initial fees.
     * @return Hashmap of phenomenons.
     */
    private HashMap<PhenomenonEnum, IPhenomenon> initPhenomenon() {
        HashMap<PhenomenonEnum, IPhenomenon> phenomenons = new HashMap<>();

        // For each possible phenomenon value, create an IPhenomenon.
        for (PhenomenonEnum phenomenon : PhenomenonEnum.values()) {
            phenomenons.put(phenomenon, new IPhenomenon() {
                final PhenomenonEnum phenomenonEnum = phenomenon;
            });
        }

        /**
         * Initialize fees
         */
        IPhenomenon snow = phenomenons.get(PhenomenonEnum.SNOW);
        snow.setFee(VehicleEnum.SCOOTER, 1.0);
        snow.setFee(VehicleEnum.BIKE, 1.0);

        IPhenomenon sleet = phenomenons.get(PhenomenonEnum.SLEET);
        sleet.setFee(VehicleEnum.SCOOTER, 1.0);
        sleet.setFee(VehicleEnum.BIKE, 1.0);

        IPhenomenon rain = phenomenons.get(PhenomenonEnum.RAIN);
        rain.setFee(VehicleEnum.SCOOTER, 0.5);
        rain.setFee(VehicleEnum.BIKE, 0.5);

        /**
         * Disallow the following:
         */
        List.of(PhenomenonEnum.GLAZE, PhenomenonEnum.HAIL, PhenomenonEnum.THUNDER)
                .forEach(phenomenon -> {
                    IPhenomenon iPhenomenon = phenomenons.get(phenomenon);
                    iPhenomenon.disallow(VehicleEnum.SCOOTER);
                    iPhenomenon.disallow(VehicleEnum.BIKE);
                });

        return phenomenons;
    }

    /**
     * Initializes the temperatures list.
     * @return Initialized temperatures list.
     */
    private List<ITemperature> initTemperature() {
        List<ITemperature> temperatures = new ArrayList<>();

        /**
         * Currently we have 2 special cases:
         * Less than -10 degrees and -10 to 0 degrees.
         * Only scooter and bike have fees.
         */
        ITemperature lessThanTen = temperature -> temperature < -10.0;
        lessThanTen.setFee(VehicleEnum.SCOOTER, 1.0);
        lessThanTen.setFee(VehicleEnum.BIKE, 1.0);

        ITemperature betweenNegativeTenAndZero = temperature -> -10.0 <= temperature && temperature <= 0.0;
        lessThanTen.setFee(VehicleEnum.SCOOTER, 0.5);
        lessThanTen.setFee(VehicleEnum.BIKE, 0.5);


        temperatures.add(lessThanTen);
        temperatures.add(betweenNegativeTenAndZero);

        return temperatures;
    }
    /**
     * Initializes the windSpeeds list.
     * @return Initialized windSpeeds list.
     */
    private List<IWindSpeed> initWindSpeed() {
        List<IWindSpeed> windSpeeds = new ArrayList<>();

        /**
         * Currently we have 2 special cases:
         * More than 20m/s and between 10m/s and 20m/s.
         * Only bike has fees/is not allowed.
         */
        IWindSpeed moreThanTwenty = speed -> speed > 20.0;
        moreThanTwenty.disallow(VehicleEnum.BIKE);

        IWindSpeed betweenTenAndTwenty = speed -> 10.0 <= speed && speed <= 20.0;
        betweenTenAndTwenty.setFee(VehicleEnum.BIKE, 0.5);


        windSpeeds.add(moreThanTwenty);
        windSpeeds.add(betweenTenAndTwenty);

        return windSpeeds;
    }

    /**
     * Method which finds the matching ITemperature instance
     * for given temperature.
     * @param temperature Given temperature value.
     * @return Matching ITemperature instance.
     */
    private ITemperature getSpecificTemperature(Double temperature) {
        return temperatures.stream()
                .filter(t -> t.isThis(temperature))
                .collect(Collectors.toList())
                .get(0);
    }

    /**
     * Method which finds the matching IWindSpeed instance
     * for given wind speed.
     * @param windSpeed Given wind speed value.
     * @return Matching IWindSpeed instance.
     */
    private IWindSpeed getSpecificWindSpeed(Double windSpeed) {
        return windSpeeds.stream()
                .filter(w -> w.isThis(windSpeed))
                .collect(Collectors.toList())
                .get(0);
    }


    /**
     * Gets the given vehicle's fee at given station.
     * @param vehicle Given vehicle.
     * @param station Given station.
     * @return Fee.
     */
    public Double getStationFee(VehicleEnum vehicle, StationEnum station) {
        return this.stations.get(station).getFee(vehicle);
    }

    /**
     * Gets the given vehicle's fee with given phenomenon.
     * @param vehicle Given vehicle.
     * @param phenomenon Given phenomenon.
     * @return Fee.
     * @throws Exception If vehicle is not allowed to drive in the weather condition.
     */
    public Double getPhenomenonFee(VehicleEnum vehicle, PhenomenonEnum phenomenon) throws Exception {
        IPhenomenon p = this.phenomenons.get(phenomenon);
        if (p.isNotAllowed(vehicle))
            throw new Exception("Usage of selected vehicle type is forbidden");
        return p.getFee(vehicle);
    }

    /**
     * Gets the given vehicle's fee with given air temperature.
     * @param vehicle Given vehicle.
     * @param temperature Given air temperature.
     * @return Fee.
     * @throws Exception If vehicle is not allowed to drive in the weather condition.
     */
    public Double getTemperatureFee(VehicleEnum vehicle, Double temperature) throws Exception {
        ITemperature t = getSpecificTemperature(temperature);
        if (t.isNotAllowed(vehicle))
            throw new Exception("Usage of selected vehicle type is forbidden");
        return t.getFee(vehicle);
    }

    /**
     * Gets the given vehicle's fee with given wind speed.
     * @param vehicle Given vehicle.
     * @param windSpeed Given wind speed.
     * @return Fee.
     * @throws Exception If vehicle is not allowed to drive in the weather condition.
     */
    public Double getWindSpeedFee(VehicleEnum vehicle, Double windSpeed) throws Exception {
        IWindSpeed w = getSpecificWindSpeed(windSpeed);
        if (w.isNotAllowed(vehicle))
            throw new Exception("Usage of selected vehicle type is forbidden");
        return getSpecificWindSpeed(windSpeed).getFee(vehicle);
    }

    /**
     * Sets the fee of given vehicle at the given station.
     * @param vehicle given vehicle.
     * @param station given station.
     * @param fee New fee.
     */
    public void setStationFee(VehicleEnum vehicle, StationEnum station, Double fee) {
        stations.get(station).setFee(vehicle, fee);
    }

    /**
     * Sets the fee of given vehicle with the given phenomenon.
     * @param vehicle given vehicle.
     * @param phenomenon given phenomenon.
     * @param fee New fee.
     */
    public void setPhenomenonFee(VehicleEnum vehicle, PhenomenonEnum phenomenon, Double fee) {
        phenomenons.get(phenomenon).setFee(vehicle, fee);
    }

    /**
     * Sets the fee of given vehicle with the given air temperature.
     * @param vehicle given vehicle.
     * @param temperature given air temperature.
     * @param fee New fee.
     */
    public void setTemperatureFee(VehicleEnum vehicle, Double temperature, Double fee) {
        getSpecificTemperature(temperature).setFee(vehicle, fee);
    }

    /**
     * Sets the fee of given vehicle with the given wind speed.
     * @param vehicle given vehicle.
     * @param windSpeed given wind speed.
     * @param fee New fee.
     */
    public void setWindSpeedFee(VehicleEnum vehicle, Double windSpeed, Double fee) {
        getSpecificWindSpeed(windSpeed).setFee(vehicle, fee);
    }

}