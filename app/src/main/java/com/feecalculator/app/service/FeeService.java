package com.feecalculator.app.service;

import com.feecalculator.app.entity.Station;
import com.feecalculator.app.enums.PhenomenonEnum;
import com.feecalculator.app.enums.StationEnum;
import com.feecalculator.app.enums.VehicleEnum;
import com.feecalculator.app.enums.WeatherEnum;
import com.feecalculator.app.error.NotAllowedError;
import com.feecalculator.app.repository.FeeRepository;
import com.feecalculator.app.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeService {

    @Autowired
    StationRepository stationRepository;

    @Autowired
    FeeRepository feeRepository;

    /**
     * Gets fee for given station name, vehicle name and timestamp.<br>
     * Finds the StationEnum and VehicleEnum.<br>
     * Gets weather data by station name and timestamp,
     * where the data is the first entry before given timestamp.<br>
     * Finds phenomenonEnum.<br>
     * Calculates fee using feeRepository methods.
     * @param station Given station name.
     * @param vehicle Given vehicle name.
     * @param timestamp Given timestamp.
     * @return Total fee.
     * @throws NotAllowedError if vehicle is not allowed for this weather condition.
     */
    public Double getFee(String station, String vehicle, Long timestamp) throws NotAllowedError {
        // Find first Station instance, where
        // name is the given name and timestamp is less than the given timestamp.
        // Also order by timestamp descending.
        StationEnum stationEnum = StationEnum.fromName(station);
        VehicleEnum vehicleEnum = VehicleEnum.fromName(vehicle);

        Station weatherData = stationRepository
                .findFirstByNameBeforeTimestamp(stationEnum.label, timestamp);

        PhenomenonEnum phenomenonEnum = PhenomenonEnum.fromName(weatherData.getPhenomenon());

        Double RBF = feeRepository.getStationFee(vehicleEnum, stationEnum);
        Double ATEF = feeRepository.getTemperatureFee(vehicleEnum, weatherData.getAirtemperature());
        Double WSEF = feeRepository.getWindSpeedFee(vehicleEnum, weatherData.getWindspeed());
        Double WPEF = feeRepository.getPhenomenonFee(vehicleEnum, phenomenonEnum);

        return RBF + ATEF + WSEF + WPEF;
    }

    /**
     * Sets the fee for the given vehicle at the given station.
     * @param station Given station.
     * @param vehicle Given vehicle.
     * @param fee New fee.
     */
    public void setStationFee(String station, String vehicle, Double fee) {
        feeRepository.setStationFee(
                VehicleEnum.fromName(vehicle),
                StationEnum.fromName(station),
                fee
        );
    }
    /**
     * Sets the fee for the given vehicle with the given phenomenon.
     * @param phenomenon Given phenomenon.
     * @param vehicle Given vehicle.
     * @param fee New fee.
     */
    public void setPhenomenonFee(String phenomenon, String vehicle, Double fee) {
        feeRepository.setPhenomenonFee(
                VehicleEnum.fromName(vehicle),
                PhenomenonEnum.fromName(phenomenon),
                fee
        );
    }
    /**
     * Sets the fee for the given vehicle with the given air temperature.
     * @param temperature Given air temperature.
     * @param vehicle Given vehicle.
     * @param fee New fee.
     */
    public void setTemperatureFee(Double temperature, String vehicle, Double fee) {
        feeRepository.setTemperatureFee(
                VehicleEnum.fromName(vehicle),
                temperature,
                fee
        );
    }
    /**
     * Sets the fee for the given vehicle with the given wind speed.
     * @param windSpeed Given wind speed.
     * @param vehicle Given vehicle.
     * @param fee New fee.
     */
    public void setWindSpeedFee(Double windSpeed, String vehicle, Double fee) {
        feeRepository.setWindSpeedFee(
                VehicleEnum.fromName(vehicle),
                windSpeed,
                fee
        );
    }

    /**
     * Sets fee according to specified parameters.
     * @param whatToSet The type of fee to set.
     * @param valToSet The specific fee to set.
     * @param vehicle The vehicle for the fee.
     * @param fee The fee.
     * @return true if no problems.
     */
    public Boolean setFee(String whatToSet,
                          String valToSet,
                          String vehicle,
                          Double fee) {
        if (whatToSet.toLowerCase().equals("city")) {
            setStationFee(valToSet, vehicle, fee);
            return true;
        }
        switch (WeatherEnum.fromName(whatToSet).label) {
            case 0 -> setPhenomenonFee(valToSet, vehicle, fee);
            case 1 -> setWindSpeedFee(Double.parseDouble(valToSet), vehicle, fee);
            case 2 -> setTemperatureFee(Double.parseDouble(valToSet), vehicle, fee);
        }
        return true;
    }
}
