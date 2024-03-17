package com.feecalculator.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.feecalculator.app.entity.XML.Observations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DatafetchService {

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

    /**
     * Maps XML string to Observations object.
     * @param XML Given XML String.
     * @return Observations object from mapped String.
     * @throws JsonProcessingException Thrown if error in processing XML.
     */

    public Observations mapXMLtoEntity(String XML) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(XML, Observations.class);
    }
}
