package com.sjsu.travelflare.services;

import com.sjsu.travelflare.models.geocode.ReverseGeocode;
import com.sjsu.travelflare.models.request.Location;
import org.springframework.stereotype.Service;

public interface GeocodingService {

    ReverseGeocode reverseGeocode(final Location location);
}
