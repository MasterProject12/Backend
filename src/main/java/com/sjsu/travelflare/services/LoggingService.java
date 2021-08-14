package com.sjsu.travelflare.services;

import com.amazonaws.services.cloudwatch.model.Metric;

public interface LoggingService {

    void log(String tag, String message);
}
