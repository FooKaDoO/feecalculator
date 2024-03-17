package com.feecalculator.app.datafetch;

import com.feecalculator.app.service.DatafetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    DatafetchService datafetchService;

    @Value("${custom.URI}")
    private String URI;

    @Scheduled(cron = "${custom.cron}")
    public void getData() {
        String data = datafetchService.fetchDataFromURI(URI);
        System.out.println("Cron");
    }
}
