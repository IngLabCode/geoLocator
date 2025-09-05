package az.naa.geoLocator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record IpApiDto(
        @JsonProperty("error")
        Boolean error,
        @JsonProperty("continentName")
        String continentName,
        @JsonProperty("country")
        String countryName,
        @JsonProperty("region")
        String regionName,
        @JsonProperty("city")
        String cityName,
        double latitude,
        double longitude
) {}
