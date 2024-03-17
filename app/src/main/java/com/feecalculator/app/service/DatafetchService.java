package com.feecalculator.app.service;

import com.feecalculator.app.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class DatafetchService {

    @Autowired
    private StationRepository stationRepository;

    /**
     * Fetches data from given URI.<br>
     * Returns as String object.
     * @param URI Given URI.
     * @return String data fetched from given URI.
     */
    public String fetchDataFromURI(String URI) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(URI, String.class);
    }
}
