package com.sjsu.travelflare.services.impl;

import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClientBuilder;
import com.amazonaws.services.cloudwatchevents.model.PutEventsRequest;
import com.amazonaws.services.cloudwatchevents.model.PutEventsRequestEntry;
import com.google.gson.JsonObject;
import com.sjsu.travelflare.aws.AWSConfigurations;
import com.sjsu.travelflare.services.LoggingService;
import org.springframework.stereotype.Service;

@Service
public class CloudWatchLoggingService implements LoggingService {

    private final AWSConfigurations awsConfigurations;
    private static final String LOG_GROUP_NAME = "TravelFlare";

    public CloudWatchLoggingService(AWSConfigurations awsConfigurations) {
        this.awsConfigurations = awsConfigurations;
    }

    @Override
    public void log(final String tag, final String message) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LOG::", message);
        AmazonCloudWatchEvents amazonCloudWatchEventsClient = AmazonCloudWatchEventsClientBuilder.defaultClient();
        PutEventsRequestEntry putEventsRequestEntry = new PutEventsRequestEntry()
                .withDetail(jsonObject.toString())
                .withDetailType(tag)
                .withSource(LOG_GROUP_NAME);

        PutEventsRequest putEventsRequest = new PutEventsRequest().withEntries(putEventsRequestEntry);
        amazonCloudWatchEventsClient.putEvents(putEventsRequest);
    }
}
