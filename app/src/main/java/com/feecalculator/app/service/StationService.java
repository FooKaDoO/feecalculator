package com.feecalculator.app.service;

import com.feecalculator.app.entity.Station;
import com.feecalculator.app.entity.XML.Observations;
import com.feecalculator.app.enums.StationEnum;
import com.feecalculator.app.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationService {

    @Autowired
    StationRepository stationRepository;

    /**
     * Maps the stations from an Observations entity to a List of
     * Station entities.<br>
     * Uses the Observations entity's timestamp. <br>
     * Ignores stations that are not in StationEnum.
     * @param observations Observations entity.
     * @return List of Stations.
     */
    public List<Station> mapObservationToStationList(Observations observations) {
        return observations.getStation().stream()
                .filter(s -> StationEnum.fromWMOCode(s.getWmocode()) != null)
                .map(xmlStation -> new Station(
                        xmlStation.getWmocode(),
                        xmlStation.getAirtemperature(),
                        xmlStation.getWindspeed(),
                        xmlStation.getPhenomenon(),
                        observations.getTimestamp()
                )).collect(Collectors.toList());
    }

    /**
     * Saves all given stations to database.
     * @param stations A list of stations to be saved to the database.
     */
    public void saveAllStationsToDB(List<Station> stations) {
        stationRepository.saveAll(stations);
    }

    public Long getCountOfDatabaseEntries() {
        return stationRepository.count();
    }


}
