package com.feecalculator.app.entity.XML;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Station {
    @JacksonXmlProperty
    private Integer wmocode;
    @JacksonXmlProperty
    private Double airtemperature;
    @JacksonXmlProperty
    private Double windspeed;
    @JacksonXmlProperty
    private String phenomenon;
}
