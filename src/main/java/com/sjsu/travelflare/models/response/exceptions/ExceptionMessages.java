package com.sjsu.travelflare.models.response.exceptions;

public enum ExceptionMessages {

    REQUIRED_FIELD_MISSING("Required field (%s) is missing in input."),
    INCORRECT_FORMAT("Incorrect format for parameter (%s).");

    private String message;

    ExceptionMessages(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
