package com.sjsu.travelflare.services.impl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehose;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehoseClient;
import com.amazonaws.services.kinesisfirehose.model.PutRecordRequest;
import com.amazonaws.services.kinesisfirehose.model.PutRecordResult;
import com.amazonaws.services.kinesisfirehose.model.Record;
import com.google.gson.JsonObject;
import com.sjsu.travelflare.aws.AWSConfigurations;
import com.sjsu.travelflare.models.request.IncidentInformation;
import com.sjsu.travelflare.services.KinesisStreamService;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Service
public class KinesisStreamServiceImpl implements KinesisStreamService {

    private final AWSConfigurations configurations;
    private static final String KINESIS_FIREHOSE_STREAM_NAME = "TravelFlare_Kinesis_Firehose";

    public KinesisStreamServiceImpl(final AWSConfigurations configurations) {
        this.configurations = configurations;
    }

    @Override
    public void putDataToKinesisFirehoseStream(final IncidentInformation incidentInformation) {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(configurations.getSecretId(), configurations.getSecretAccess());
        AmazonKinesisFirehose kinesisFirehoseClient = AmazonKinesisFirehoseClient.builder().withRegion(Regions.US_WEST_2.getName()).withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials)).build();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("latitude", incidentInformation.getLocation().getLatitude());
        jsonObject.addProperty("longitude", incidentInformation.getLocation().getLongitude());

        PutRecordRequest putRecordRequest = new PutRecordRequest();
        putRecordRequest.setDeliveryStreamName(KINESIS_FIREHOSE_STREAM_NAME);

        Record record = new Record().withData(ByteBuffer.wrap(jsonObject.toString().getBytes(StandardCharsets.UTF_8)));
        putRecordRequest.setRecord(record);
        kinesisFirehoseClient.putRecord(putRecordRequest);
    }
}
