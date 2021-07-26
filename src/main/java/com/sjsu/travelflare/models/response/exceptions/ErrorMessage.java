package com.sjsu.travelflare.models.response.exceptions;

import java.util.Date;

public class ErrorMessage {

    private String message;
    private Date date;

    public ErrorMessage(String message, Date date) {
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
