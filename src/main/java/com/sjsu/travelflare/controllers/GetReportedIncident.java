package com.sjsu.travelflare.controllers;

import com.sjsu.travelflare.models.geocode.ReverseGeocode;
import com.sjsu.travelflare.models.request.IncidentInformation;
import com.sjsu.travelflare.models.request.Location;
import com.sjsu.travelflare.services.DataStorage;
import com.sjsu.travelflare.services.GeocodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/incident/get")
public class GetReportedIncident {

    @Autowired
    private GeocodingService geocodingService;

    @GetMapping
    public List<IncidentInformation> getIncident(@RequestParam Map<String, String> queryParams) {
        final String latLon = queryParams.get("location");
        final String[] coordinates = latLon.split(",");
        Location location = new Location();
        location.setLatitude(coordinates[0]);
        location.setLongitude(coordinates[1]);
        ReverseGeocode reverseGeocode = geocodingService.reverseGeocode(location);
        final String key = reverseGeocode.toString();
        final List<IncidentInformation> incidentInformation = DataStorage.data.getOrDefault(key, new ArrayList<>());
        return incidentInformation;
    }
}
