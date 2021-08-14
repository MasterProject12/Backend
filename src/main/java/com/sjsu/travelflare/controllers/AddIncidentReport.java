package com.sjsu.travelflare.controllers;

import com.sjsu.travelflare.dto.IncidentDto;
import com.sjsu.travelflare.models.geocode.ReverseGeocode;
import com.sjsu.travelflare.models.request.IncidentInformation;
import com.sjsu.travelflare.models.request.Location;
import com.sjsu.travelflare.models.response.AddIncidentResponse;
import com.sjsu.travelflare.models.response.ResponseMessages;
import com.sjsu.travelflare.models.response.exceptions.ExceptionMessages;
import com.sjsu.travelflare.models.response.exceptions.ParameterMissingException;
import com.sjsu.travelflare.services.GeocodingService;
import com.sjsu.travelflare.services.IncidentService;
import com.sjsu.travelflare.services.KinesisStreamService;
import com.sjsu.travelflare.services.LoggingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/incident/add")
public class AddIncidentReport {

    private static final String TAG = "AddIncidentReport";

    @Autowired
    private GeocodingService geocodingService;

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private KinesisStreamService kinesisStreamService;

    @Autowired
    private LoggingService loggingService;

    @PostMapping
    public AddIncidentResponse addIncident(@RequestBody IncidentInformation incident) throws Exception {
        if (incident.getLocation() == null) {
            throw new ParameterMissingException(String.format(ExceptionMessages.REQUIRED_FIELD_MISSING.getMessage(), "Location"));
        }

        if (incident.getIncidentData() == null) {
            throw new ParameterMissingException(String.format(ExceptionMessages.REQUIRED_FIELD_MISSING.getMessage(), "Incident Data"));
        }

        ReverseGeocode reverseGeocode = geocodingService.reverseGeocode(incident.getLocation());
        IncidentDto incidentDto = new IncidentDto();
        BeanUtils.copyProperties(incident.getLocation(), incidentDto);
        BeanUtils.copyProperties(incident.getIncidentData(), incidentDto);
        incidentDto.setCity(reverseGeocode.toString());

        IncidentDto incidentDtoResult = incidentService.addIncident(incidentDto);
        Location location = new Location();
        location.setLongitude(incidentDtoResult.getLongitude());
        location.setLatitude(incidentDtoResult.getLatitude());
        kinesisStreamService.putDataToKinesisFirehoseStream(incident);
        loggingService.log(TAG, "Successfully added incident." + incident.getLocation().toString());
        return new AddIncidentResponse(ResponseMessages.SUCCESS, location);
    }
}
