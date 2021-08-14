package com.sjsu.travelflare.services;

import com.sjsu.travelflare.models.request.IncidentInformation;

public interface KinesisStreamService {
    void putDataToKinesisFirehoseStream(final IncidentInformation incidentInformation);
}
