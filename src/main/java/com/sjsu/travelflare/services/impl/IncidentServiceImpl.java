package com.sjsu.travelflare.services.impl;

import com.sjsu.travelflare.dto.IncidentDto;
import com.sjsu.travelflare.models.entity.IncidentEntity;
import com.sjsu.travelflare.repository.IncidentsRepository;
import com.sjsu.travelflare.services.IncidentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IncidentServiceImpl implements IncidentService {

    @Autowired
    private IncidentsRepository incidentsRepository;

    @Override
    public IncidentDto addIncident(final IncidentDto incidentDto) {
        IncidentEntity incidentEntity = new IncidentEntity();
        BeanUtils.copyProperties(incidentDto, incidentEntity);
        IncidentEntity incidentEntity1 = incidentsRepository.insert(incidentEntity);
        IncidentDto incidentDto1 = new IncidentDto();
        BeanUtils.copyProperties(incidentEntity1, incidentDto1);
        return incidentDto1;
    }

    @Override
    public List<IncidentDto> getIncidents(final String city) {
        List<IncidentEntity> incidentEntityList = incidentsRepository.getIncidentEntitiesByCity(city);
        List<IncidentDto> incidentDtoList = new ArrayList<>(incidentEntityList.size());
        for (final IncidentEntity incidentEntity : incidentEntityList) {
            IncidentDto incidentDto = new IncidentDto();
            BeanUtils.copyProperties(incidentEntity, incidentDto);
            incidentDtoList.add(incidentDto);
        }
        return incidentDtoList;
    }
}
