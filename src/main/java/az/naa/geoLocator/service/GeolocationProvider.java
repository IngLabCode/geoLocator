package az.naa.geoLocator.service;


import az.naa.geoLocator.dto.GeoResponse;

public interface GeolocationProvider {
    GeoResponse getByIp(String ip);
}
