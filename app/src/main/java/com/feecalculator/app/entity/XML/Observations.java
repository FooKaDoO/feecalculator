package com.feecalculator.app.entity.XML;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Observations {

    @JacksonXmlProperty(isAttribute = true)
    private Long timestamp;

    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Station> station;
}
