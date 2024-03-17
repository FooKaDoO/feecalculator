package com.feecalculator.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="custom")
public class Props {

    private String cron;
    private String URI;

}
