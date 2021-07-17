package com.sjsu.travelflare.models.request;

public class IncidentInformation {

    private Location location;
    private IncidentData incidentData;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public IncidentData getIncidentData() {
        return incidentData;
    }

    public void setIncidentData(IncidentData incidentData) {
        this.incidentData = incidentData;
    }
}
