package com.feecalculator.app.datafetch;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Scheduled(cron = "*/30 * * * * ?")
    public void getData() {

    }
}
