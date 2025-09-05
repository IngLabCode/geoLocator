package az.naa.geoLocator.service.impl;

import az.naa.geoLocator.dto.IpApiDto;
import az.naa.geoLocator.dto.GeoResponse;
import az.naa.geoLocator.service.GeolocationProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.security.ProviderException;

@Component
public class IpApiClient implements GeolocationProvider {

    private final RestTemplate restTemplate;

    @Value("${url.template}")
    private String URL_TEMPLATE;

    public IpApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public GeoResponse getByIp(String ip) {
        int maxRetries = 3;
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                String url = String.format(URL_TEMPLATE, ip);

                ResponseEntity<IpApiDto> response = restTemplate.getForEntity(url, IpApiDto.class);
                IpApiDto dto = response.getBody();

                if (dto == null) {
                    throw new ProviderException("Null response");
                }

                if (dto.error() != null && !dto.error())  {
                    throw new ProviderException("Invalid IP or API error");
                }

                return new GeoResponse(
                        dto.continentName(),
                        dto.countryName(),
                        dto.regionName(),
                        dto.cityName(),
                        dto.latitude(),
                        dto.longitude()
                );

            } catch (HttpClientErrorException ex) {
                throw new ProviderException("Invalid IP format");
            } catch (ResourceAccessException ignored) {
            } catch (Exception ex) {
                throw new ProviderException("Failed to fetch GeoLocation", ex);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        throw new ProviderException("Failed to fetch GeoLocation after retries");
    }

}
