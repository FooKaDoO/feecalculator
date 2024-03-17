package com.feecalculator.app.service;

import com.feecalculator.app.enums.StationEnum;
import com.feecalculator.app.repository.FeeRepository;
import com.feecalculator.app.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeService {

    @Autowired
    StationRepository stationRepository;

    @Autowired
    FeeRepository feeRepository;

    public Double getFee(String station, String vehicle, Long timestamp) {
        StationEnum stationEnum = StationEnum.fromName(station);

        return 0.0;
    }
}
