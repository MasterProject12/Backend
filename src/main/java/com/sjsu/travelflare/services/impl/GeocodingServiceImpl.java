package com.sjsu.travelflare.services.impl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.location.AmazonLocation;
import com.amazonaws.services.location.AmazonLocationClientBuilder;
import com.amazonaws.services.location.model.Place;
import com.amazonaws.services.location.model.SearchForPositionResult;
import com.amazonaws.services.location.model.SearchPlaceIndexForPositionRequest;
import com.amazonaws.services.location.model.SearchPlaceIndexForPositionResult;
import com.sjsu.travelflare.aws.AWSConfigurations;
import com.sjsu.travelflare.models.geocode.ReverseGeocode;
import com.sjsu.travelflare.models.networking.Networking;
import com.sjsu.travelflare.models.request.Location;
import com.sjsu.travelflare.services.GeocodingService;
import com.sjsu.travelflare.services.LoggingService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class GeocodingServiceImpl implements GeocodingService {

    private static final String TAG = "GeocodingServiceImpl";
    private final Networking networking;
    private final AWSConfigurations awsConfigurations;
    private final LoggingService loggingService;
    private final AmazonLocation amazonLocation;

    public GeocodingServiceImpl(final Networking networking, final AWSConfigurations awsConfigurations, final CloudWatchLoggingService loggingService) {
        this.networking = networking;
        this.awsConfigurations = awsConfigurations;
        this.loggingService = loggingService;
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(awsConfigurations.getSecretId(), awsConfigurations.getSecretAccess());
        amazonLocation = AmazonLocationClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion("us-west-2").build();
    }

    public ReverseGeocode reverseGeocode(final Location location) {

        SearchPlaceIndexForPositionRequest searchPlaceIndexForPositionRequest = new SearchPlaceIndexForPositionRequest();
        searchPlaceIndexForPositionRequest.setIndexName(awsConfigurations.getPlaceIndex());
        searchPlaceIndexForPositionRequest.setMaxResults(1);
        searchPlaceIndexForPositionRequest.setPosition(Arrays.asList(Double.parseDouble(location.getLongitude()), Double.parseDouble(location.getLatitude())));

        SearchPlaceIndexForPositionResult searchPlaceIndexForPositionResult = amazonLocation.searchPlaceIndexForPosition(searchPlaceIndexForPositionRequest);

        List<SearchForPositionResult> searchForPositionResultList = searchPlaceIndexForPositionResult.getResults();
        if (searchForPositionResultList != null && !searchForPositionResultList.isEmpty()) {
            SearchForPositionResult searchForPositionResult = searchForPositionResultList.get(0);
            Place place = searchForPositionResult.getPlace();
            return new ReverseGeocode(place.getCountry(), place.getRegion(), place.getSubRegion());
        }

        loggingService.log(TAG, "Error in reverse geocoding the location" + location.toString());
        return null;
    }
}
