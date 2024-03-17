package com.feecalculator.app.datafetch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.feecalculator.app.entity.Station;
import com.feecalculator.app.entity.XML.Observations;
import com.feecalculator.app.service.DatafetchService;
import com.feecalculator.app.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseInit {

    @Autowired
    DatafetchService datafetchService;
    @Autowired
    StationService stationService;

    /**
     * URI taken from application.properties.
     */
    @Value("${custom.URI}")
    private String URI;

    /**
     * Scheduling taken from application.properties.<br>
     * Fetches data by URI.<br>
     * Maps all Observations entity stations to a list of stations. <br>
     * Saves the list of stations to the database. <br>
     * <br>
     * In case of XML parsing errors, prints them to the terminal.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void getData() {
        String data = datafetchService.fetchDataFromURI(URI);
        try {
            Observations observations = datafetchService.mapXMLtoEntity(data);
            List<Station> stations = stationService.mapObservationToStationList(observations);
            stationService.saveAllStationsToDB(stations);
        } catch (JsonProcessingException e) {
            System.out.println("XML parsing exception:");
            System.out.println(e.getMessage());
        }
    }
}
