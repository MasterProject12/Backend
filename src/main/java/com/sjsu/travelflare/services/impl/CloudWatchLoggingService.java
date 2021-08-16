package com.sjsu.travelflare.services.impl;

import com.sjsu.travelflare.aws.AWSConfigurations;
import com.sjsu.travelflare.services.LoggingService;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.InputLogEvent;
import software.amazon.awssdk.services.cloudwatchlogs.model.PutLogEventsRequest;
import software.amazon.awssdk.services.cloudwatchlogs.model.PutLogEventsResponse;

@Service
public class CloudWatchLoggingService implements LoggingService {

    private final AWSConfigurations awsConfigurations;
    private final CloudWatchLogsClient cloudWatchLogsClient;
    private static final String LOG_GROUP_NAME = "TravelFlare";
    private static final String LOG_STREAM_NAME = "travelflare";
    private String sequenceToken;

    public CloudWatchLoggingService(AWSConfigurations awsConfigurations) {
        this.awsConfigurations = awsConfigurations;
        cloudWatchLogsClient = CloudWatchLogsClient.builder().credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(awsConfigurations.getSecretId(), awsConfigurations.getSecretAccess()))).build();
    }

    @Override
    public void log(final String tag, final String message) {
        InputLogEvent inputLogEvent = InputLogEvent.builder()
                .message(message)
                .timestamp(System.currentTimeMillis())
                .build();
        PutLogEventsRequest putEventsRequest = PutLogEventsRequest.builder()
                .logGroupName(LOG_GROUP_NAME)
                .logStreamName(LOG_STREAM_NAME)
                .sequenceToken(sequenceToken)
                .logEvents(inputLogEvent)
                .build();
        PutLogEventsResponse putLogEventsResponse = cloudWatchLogsClient.putLogEvents(putEventsRequest);
        if (putLogEventsResponse != null) {
            sequenceToken = putLogEventsResponse.nextSequenceToken();
        }
    }
}
