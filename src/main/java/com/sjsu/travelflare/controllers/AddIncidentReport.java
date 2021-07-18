package com.sjsu.travelflare.controllers;

import com.sjsu.travelflare.models.geocode.ReverseGeocode;
import com.sjsu.travelflare.services.DataStorage;
import com.sjsu.travelflare.services.GeocodingService;
import com.sjsu.travelflare.models.request.IncidentInformation;
import com.sjsu.travelflare.models.response.AddIncidentResponse;
import com.sjsu.travelflare.models.response.ResponseMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/incident/add")
public class AddIncidentReport {

    @Autowired
    private GeocodingService geocodingService;

    @PostMapping
    public AddIncidentResponse addIncident(@RequestBody IncidentInformation incident) {
        ReverseGeocode reverseGeocode = geocodingService.reverseGeocode(incident.getLocation());
        final String key = reverseGeocode.toString();
        ArrayList<IncidentInformation> locationList = DataStorage.data.getOrDefault(key, new ArrayList<>());
        locationList.add(0, incident);
        DataStorage.data.put(key, locationList);
        return new AddIncidentResponse(ResponseMessages.SUCCESS, incident.getLocation());
    }
}
