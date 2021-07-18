package com.sjsu.travelflare.models.response;

import com.sjsu.travelflare.models.request.Location;

public class AddIncidentResponse {

    private final String result;
    private final Location locationAdded;

    public AddIncidentResponse(final ResponseMessages responseMessages, final Location locationAdded) {
        result = responseMessages.getMessage();
        this.locationAdded = locationAdded;
    }

    public String getResult() {
        return result;
    }

    public Location getLocationAdded() {
        return locationAdded;
    }
}
