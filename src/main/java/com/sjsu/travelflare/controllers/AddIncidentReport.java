package com.sjsu.travelflare.controllers;

import com.sjsu.travelflare.models.geocode.ReverseGeocode;
import com.sjsu.travelflare.models.response.exceptions.ErrorMessage;
import com.sjsu.travelflare.models.response.exceptions.ExceptionMessages;
import com.sjsu.travelflare.models.response.exceptions.ParameterMissingException;
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
    public AddIncidentResponse addIncident(@RequestBody IncidentInformation incident) throws Exception {
        if (incident.getLocation() == null) {
            throw new ParameterMissingException(String.format(ExceptionMessages.REQUIRED_FIELD_MISSING.getMessage(), "Location"));
        }

        if (incident.getIncidentData() == null) {
            throw new ParameterMissingException(String.format(ExceptionMessages.REQUIRED_FIELD_MISSING.getMessage(), "Incident Data"));
        }
        ReverseGeocode reverseGeocode = geocodingService.reverseGeocode(incident.getLocation());
        final String key = reverseGeocode.toString();
        ArrayList<IncidentInformation> locationList = DataStorage.data.getOrDefault(key, new ArrayList<>());
        locationList.add(0, incident);
        DataStorage.data.put(key, locationList);
        return new AddIncidentResponse(ResponseMessages.SUCCESS, incident.getLocation());
    }
}
