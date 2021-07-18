package com.sjsu.travelflare.models.response;

public enum ResponseMessages {
    SUCCESS("Success"), FAILURE("Failure");

    private final String message;

    ResponseMessages(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
