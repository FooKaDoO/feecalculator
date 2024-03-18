package com.feecalculator.app.repository;

import com.feecalculator.app.dto.IStation;
import com.feecalculator.app.dto.IWeatherTypes.IPhenomenon;
import com.feecalculator.app.dto.IWeatherTypes.ITemperature;
import com.feecalculator.app.dto.IWeatherTypes.IWindSpeed;
import com.feecalculator.app.enums.PhenomenonEnum;
import com.feecalculator.app.enums.StationEnum;
import com.feecalculator.app.enums.VehicleEnum;
import com.feecalculator.app.error.NotAllowedError;
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
                public Double getFee(VehicleEnum vehicle) {
                    return fees[vehicle.label];
                };
                public void setFee(VehicleEnum vehicle, Double fee) {
                    fees[vehicle.label] = fee;
                }
                public void removeFee(VehicleEnum vehicle) {
                    fees[vehicle.label] = 0.0;
                }
                double[] fees = new double[VehicleEnum.values().length];
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
                @Override
                public boolean isThis(Double val) {
                    if (phenomenonEnum == null)
                        return false;
                    return val.equals(phenomenonEnum.label);
                }

                public Double getFee(VehicleEnum vehicle) {
                    return fees[vehicle.label];
                };


                public void setFee(VehicleEnum vehicle, Double fee) {
                    fees[vehicle.label] = fee;
                }


                public void removeFee(VehicleEnum vehicle) {
                    fees[vehicle.label] = 0.0;
                }

                double[] fees = new double[VehicleEnum.values().length];

                public boolean isNotAllowed(VehicleEnum vehicle) {
                    return notAllowedVehicles[vehicle.label];
                }

                public void allow(VehicleEnum vehicle) {
                    notAllowedVehicles[vehicle.label] = false;
                }

                public void disallow(VehicleEnum vehicle) {
                    notAllowedVehicles[vehicle.label] = true;
                }

                boolean[] notAllowedVehicles = new boolean[VehicleEnum.values().length];


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
        ITemperature lessThanTen = new ITemperature() {
            @Override
            public boolean isThis(Double val) {
                return val < -10.0;
            }
            public Double getFee(VehicleEnum vehicle) {
                return fees[vehicle.label];
            };


            public void setFee(VehicleEnum vehicle, Double fee) {
                fees[vehicle.label] = fee;
            }


            public void removeFee(VehicleEnum vehicle) {
                fees[vehicle.label] = 0.0;
            }

            double[] fees = new double[VehicleEnum.values().length];

            public boolean isNotAllowed(VehicleEnum vehicle) {
                return notAllowedVehicles[vehicle.label];
            }

            public void allow(VehicleEnum vehicle) {
                notAllowedVehicles[vehicle.label] = false;
            }

            public void disallow(VehicleEnum vehicle) {
                notAllowedVehicles[vehicle.label] = true;
            }

            boolean[] notAllowedVehicles = new boolean[VehicleEnum.values().length];
        };
        lessThanTen.setFee(VehicleEnum.SCOOTER, 1.0);
        lessThanTen.setFee(VehicleEnum.BIKE, 1.0);

        ITemperature betweenNegativeTenAndZero = new ITemperature() {
            @Override
            public boolean isThis(Double val) {
                return -10.0 <= val && val <= 0.0;
            }


            public Double getFee(VehicleEnum vehicle) {
                return fees[vehicle.label];
            };


            public void setFee(VehicleEnum vehicle, Double fee) {
                fees[vehicle.label] = fee;
            }


            public void removeFee(VehicleEnum vehicle) {
                fees[vehicle.label] = 0.0;
            }

            double[] fees = new double[VehicleEnum.values().length];

            public boolean isNotAllowed(VehicleEnum vehicle) {
                return notAllowedVehicles[vehicle.label];
            }

            public void allow(VehicleEnum vehicle) {
                notAllowedVehicles[vehicle.label] = false;
            }

            public void disallow(VehicleEnum vehicle) {
                notAllowedVehicles[vehicle.label] = true;
            }

            boolean[] notAllowedVehicles = new boolean[VehicleEnum.values().length];
        };
        betweenNegativeTenAndZero.setFee(VehicleEnum.SCOOTER, 0.5);
        betweenNegativeTenAndZero.setFee(VehicleEnum.BIKE, 0.5);


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
        IWindSpeed moreThanTwenty = new IWindSpeed() {
            @Override
            public boolean isThis(Double val) {
                return val > 20.0;
            }

            public Double getFee(VehicleEnum vehicle) {
                return fees[vehicle.label];
            };


            public void setFee(VehicleEnum vehicle, Double fee) {
                fees[vehicle.label] = fee;
            }


            public void removeFee(VehicleEnum vehicle) {
                fees[vehicle.label] = 0.0;
            }

            double[] fees = new double[VehicleEnum.values().length];

            public boolean isNotAllowed(VehicleEnum vehicle) {
                return notAllowedVehicles[vehicle.label];
            }

            public void allow(VehicleEnum vehicle) {
                notAllowedVehicles[vehicle.label] = false;
            }

            public void disallow(VehicleEnum vehicle) {
                notAllowedVehicles[vehicle.label] = true;
            }

            boolean[] notAllowedVehicles = new boolean[VehicleEnum.values().length];
        };
        moreThanTwenty.disallow(VehicleEnum.BIKE);

        IWindSpeed betweenTenAndTwenty = new IWindSpeed() {
            @Override
            public boolean isThis(Double val) {
                return 10.0 <= val && val <= 20.0;
            }

            public Double getFee(VehicleEnum vehicle) {
                return fees[vehicle.label];
            };


            public void setFee(VehicleEnum vehicle, Double fee) {
                fees[vehicle.label] = fee;
            }


            public void removeFee(VehicleEnum vehicle) {
                fees[vehicle.label] = 0.0;
            }

            double[] fees = new double[VehicleEnum.values().length];

            public boolean isNotAllowed(VehicleEnum vehicle) {
                return notAllowedVehicles[vehicle.label];
            }

            public void allow(VehicleEnum vehicle) {
                notAllowedVehicles[vehicle.label] = false;
            }

            public void disallow(VehicleEnum vehicle) {
                notAllowedVehicles[vehicle.label] = true;
            }

            boolean[] notAllowedVehicles = new boolean[VehicleEnum.values().length];
        };
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
     * @throws IndexOutOfBoundsException if there isn't such a temperature instance.
     */
    private ITemperature getSpecificTemperature(Double temperature) throws IndexOutOfBoundsException  {
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
     * @throws IndexOutOfBoundsException if there isn't such a windspeed instance.
     */
    private IWindSpeed getSpecificWindSpeed(Double windSpeed) throws IndexOutOfBoundsException {
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
     * Gets the given vehicle's fee with given phenomenon. <br>
     * If there is no phenomenon, returns 0.0
     * @param vehicle Given vehicle.
     * @param phenomenon Given phenomenon.
     * @return Fee.
     * @throws NotAllowedError If vehicle is not allowed to drive in the weather condition.
     */
    public Double getPhenomenonFee(VehicleEnum vehicle, PhenomenonEnum phenomenon) throws NotAllowedError {
        if (phenomenon == null)
            return 0.0;
        IPhenomenon p = this.phenomenons.get(phenomenon);
        if (p.isNotAllowed(vehicle))
            throw new NotAllowedError();
        return p.getFee(vehicle);
    }

    /**
     * Gets the given vehicle's fee with given air temperature.<br>
     * If there does not exist an instance like this, returns 0.0
     * @param vehicle Given vehicle.
     * @param temperature Given air temperature.
     * @return Fee.
     * @throws NotAllowedError If vehicle is not allowed to drive in the weather condition.
     */
    public Double getTemperatureFee(VehicleEnum vehicle, Double temperature) throws NotAllowedError {
        try {
            ITemperature t = getSpecificTemperature(temperature);
            if (t.isNotAllowed(vehicle))
                throw new NotAllowedError();
            return t.getFee(vehicle);
        } catch (IndexOutOfBoundsException e) {
            return 0.0;
        }
    }

    /**
     * Gets the given vehicle's fee with given wind speed.<br>
     * If there does not exist an instance like this, returns 0.0
     * @param vehicle Given vehicle.
     * @param windSpeed Given wind speed.
     * @return Fee.
     * @throws NotAllowedError If vehicle is not allowed to drive in the weather condition.
     */
    public Double getWindSpeedFee(VehicleEnum vehicle, Double windSpeed) throws NotAllowedError {
        try {
            IWindSpeed w = getSpecificWindSpeed(windSpeed);
            if (w.isNotAllowed(vehicle))
                throw new NotAllowedError();
            return getSpecificWindSpeed(windSpeed).getFee(vehicle);
        } catch (IndexOutOfBoundsException e) {
            return 0.0;
        }
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
     * Sets the fee of given vehicle with the given air temperature.<br>
     * If such an instance does not exist, doesn't do anything.
     * @param vehicle given vehicle.
     * @param temperature given air temperature.
     * @param fee New fee.
     */
    public void setTemperatureFee(VehicleEnum vehicle, Double temperature, Double fee) {
        try {
            getSpecificTemperature(temperature).setFee(vehicle, fee);
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Sets the fee of given vehicle with the given wind speed.<br>
     * If such an instance does not exist, doesn't do anything.
     * @param vehicle given vehicle.
     * @param windSpeed given wind speed.
     * @param fee New fee.
     */
    public void setWindSpeedFee(VehicleEnum vehicle, Double windSpeed, Double fee) {
        try {
            getSpecificWindSpeed(windSpeed).setFee(vehicle, fee);
        } catch (IndexOutOfBoundsException e) {

        }
    }

}
