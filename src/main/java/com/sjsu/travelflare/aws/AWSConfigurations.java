package com.sjsu.travelflare.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Configuration
@PropertySource("classpath:aws.properties")
public class AWSConfigurations {

    @Value("${aws.dbName}")
    private String dbName;

    @Value("${aws.secretId}")
    private String secretId;

    @Value("${aws.secretAccess}")
    private String secretAccess;

    @Value("${aws.placeindex}")
    private String placeIndex;

    public String getDbName() {
        return dbName;
    }

    public String getSecretId() {
        return secretId;
    }

    public String getSecretAccess() {
        return secretAccess;
    }

    public String getPlaceIndex() {
        return placeIndex;
    }
}
