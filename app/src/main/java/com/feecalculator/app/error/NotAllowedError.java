package com.feecalculator.app.error;

public class NotAllowedError extends Error {
    public NotAllowedError() {
        super("Usage of selected vehicle type is forbidden");
    }
}
