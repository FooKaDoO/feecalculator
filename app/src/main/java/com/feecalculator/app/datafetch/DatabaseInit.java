package com.feecalculator.app.datafetch;

import com.feecalculator.app.service.DatafetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit {

    @Autowired
    DatafetchService datafetchService;

    @Value("${custom.URI}")
    private String URI;

    @EventListener(ApplicationReadyEvent.class)
    public void getData() {
        String data = datafetchService.fetchDataFromURI(URI);
        System.out.println("Init");
    }
}
