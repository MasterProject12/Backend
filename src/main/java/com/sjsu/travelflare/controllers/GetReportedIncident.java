package com.sjsu.travelflare.controllers;

import com.sjsu.travelflare.dto.IncidentDto;
import com.sjsu.travelflare.models.geocode.ReverseGeocode;
import com.sjsu.travelflare.models.request.IncidentData;
import com.sjsu.travelflare.models.request.IncidentInformation;
import com.sjsu.travelflare.models.request.Location;
import com.sjsu.travelflare.models.response.exceptions.ExceptionMessages;
import com.sjsu.travelflare.models.response.exceptions.IncorrectFormatException;
import com.sjsu.travelflare.models.response.exceptions.ParameterMissingException;
import com.sjsu.travelflare.services.IncidentService;
import com.sjsu.travelflare.services.GeocodingService;
import org.springframework.beans.BeanUtils;
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
    private IncidentService incidentService;

    @Autowired
    private GeocodingService geocodingService;

    @GetMapping
    public List<IncidentInformation> getIncident(@RequestParam Map<String, String> queryParams) throws Exception {
        final String latLon = queryParams.get("location");
        if (latLon == null) {
            throw new ParameterMissingException(String.format(ExceptionMessages.REQUIRED_FIELD_MISSING.getMessage(), "Location"));
        }
        final String[] coordinates = latLon.split(",");
        if (coordinates.length != 2) {
            throw new IncorrectFormatException(String.format(ExceptionMessages.INCORRECT_FORMAT.getMessage(), "Location"));
        }
        Location location = new Location();
        location.setLatitude(coordinates[0]);
        location.setLongitude(coordinates[1]);
        ReverseGeocode reverseGeocode = geocodingService.reverseGeocode(location);
        final String key = reverseGeocode.toString();
        List<IncidentDto> incidentDtos = incidentService.getIncidents(key);
        List<IncidentInformation> incidentInformationList = new ArrayList<>(incidentDtos.size());
        for (final IncidentDto incidentDto : incidentDtos) {
            IncidentInformation incidentInformation = new IncidentInformation();
            Location location1 = new Location();
            BeanUtils.copyProperties(incidentDto, location1);
            incidentInformation.setLocation(location1);
            IncidentData incidentData = new IncidentData();
            BeanUtils.copyProperties(incidentDto, incidentData);
            incidentInformation.setIncidentData(incidentData);
            incidentInformationList.add(incidentInformation);
        }
        return incidentInformationList;
    }
}
