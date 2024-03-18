package com.feecalculator.app.controller;


import com.feecalculator.app.error.NotAllowedError;
import com.feecalculator.app.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/fee")
@CrossOrigin("http://localhost:8080/")
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
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Error getting data, try adjusting input parameters.",
                    e.getCause()
            );
        }

    }

    @PostMapping
    public ResponseEntity<Boolean> setFee(@RequestParam String whatToSet,
                                         @RequestParam String valToSet,
                                         @RequestParam String vehicle,
                                         @RequestParam Double fee) {
        return new ResponseEntity<>(
                feeService.setFee(
                        whatToSet,
                        valToSet,
                        vehicle,
                        fee),
                HttpStatus.OK);
    }
}
