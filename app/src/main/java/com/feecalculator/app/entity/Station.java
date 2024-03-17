package com.feecalculator.app.entity;


import com.feecalculator.app.enums.StationEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter
    private String name;

    @Setter
    private Integer wmocode;

    @Setter
    private Double airtemperature;

    @Setter
    private Double windspeed;

    @Setter
    private String phenomenon;

    @Setter
    private Long timestamp;

    /**
     * Constructor for creating a Station entity, without using station name.<br>
     * Uses StationEnum to map WMO code to station name.
     * @param wmocode WMO code of station.
     * @param airtemperature Air temperature at the given time (timestamp) from the station.
     * @param windspeed Wind speed at the given time (timestamp) from the station.
     * @param phenomenon Weather phenomenon at the given time (timestamp) from the station.
     * @param timestamp Timestamp of the measurement.
     */
    public Station(Integer wmocode,
                   Double airtemperature,
                   Double windspeed,
                   String phenomenon,
                   Long timestamp) {
        this.name = StationEnum.fromWMOCode(wmocode).label;
        this.wmocode = wmocode;
        this.airtemperature = airtemperature;
        this.windspeed = windspeed;
        this.phenomenon = phenomenon;
        this.timestamp = timestamp;
    }
}
