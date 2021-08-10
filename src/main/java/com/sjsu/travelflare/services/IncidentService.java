package com.sjsu.travelflare.services;

import com.sjsu.travelflare.dto.IncidentDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IncidentService {

    IncidentDto addIncident(final IncidentDto incident);
    List<IncidentDto> getIncidents(final String city);
}
