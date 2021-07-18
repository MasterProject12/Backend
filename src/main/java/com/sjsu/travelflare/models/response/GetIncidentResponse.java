package com.sjsu.travelflare.models.response;

import com.sjsu.travelflare.models.request.Location;

public class GetIncidentResponse {

    private final Location[] locationsToMonitor;

    public GetIncidentResponse(Location[] locationsToMonitor) {
        this.locationsToMonitor = locationsToMonitor;
    }
}
