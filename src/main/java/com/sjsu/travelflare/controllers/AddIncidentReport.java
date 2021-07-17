package com.sjsu.travelflare.controllers;

import com.sjsu.travelflare.models.request.IncidentInformation;
import com.sjsu.travelflare.storage.DataStorage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/incident/add")
public class AddIncidentReport {

    @PostMapping
    public String addIncident(@RequestBody IncidentInformation incident) {
        final String key = incident.getLocation().getLatitude() + "," + incident.getLocation().getLongitude();
        DataStorage.data.put(key, incident);
        return "Incident Added";
    }
}
