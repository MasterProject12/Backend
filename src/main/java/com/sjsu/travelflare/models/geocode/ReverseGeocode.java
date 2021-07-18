package com.sjsu.travelflare.models.geocode;

public class ReverseGeocode {

    private final String country;
    private final String state;
    private final String city;

    public ReverseGeocode(String country, String state, String city) {
        this.country = country;
        this.state = state;
        this.city = city;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(country);
        stringBuilder.append("-");
        stringBuilder.append(state);
        stringBuilder.append("-");
        stringBuilder.append(city);
        return stringBuilder.toString();
    }
}
