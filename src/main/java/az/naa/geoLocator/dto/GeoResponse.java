package az.naa.geoLocator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeoResponse {
    String Continent;
    String Country;
    String Region;
    String City;
    double Latitude;
    double Longitude;

}