package com.sjsu.travelflare.controllers;

import com.sjsu.travelflare.models.request.IncidentInformation;
import com.sjsu.travelflare.storage.DataStorage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/incident/get")
public class GetReportedIncident {

    @GetMapping
    public IncidentInformation getIncident(@RequestParam Map<String, String> queryParams) {
        final String key = queryParams.get("location");
        return DataStorage.data.get(key);
    }
}
