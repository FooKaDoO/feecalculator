package com.feecalculator.app.controller;


import com.feecalculator.app.error.NotAllowedError;
import com.feecalculator.app.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/fee")
public class FeeController {

    @Autowired
    FeeService feeService;

    @GetMapping
    public ResponseEntity<Double> getFee(@RequestParam String station,
                                         @RequestParam String vehicle,
                                         @RequestParam Long timestamp) {
        try {
            return new ResponseEntity<>(
                    feeService.getFee(
                    station,
                    vehicle,
                    timestamp),
                    HttpStatus.OK);
        } catch (NotAllowedError e) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    e.getMessage(),
                    e.getCause()
            );
        }

    }
}
