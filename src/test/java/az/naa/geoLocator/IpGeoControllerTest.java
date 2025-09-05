package az.naa.geoLocator;

import az.naa.geoLocator.controller.IpGeoController;
import az.naa.geoLocator.dto.GeoResponse;
import az.naa.geoLocator.service.impl.GeolocationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class IpGeoControllerTest {
    @Mock
    private GeolocationService geolocationService;

    @InjectMocks
    private IpGeoController ipGeoController;

    @Test
    void testLookup() {
        String ip = "8.8.8.8";
        GeoResponse mockResponse = new GeoResponse(
                "North America",
                "United States",
                "California",
                "Mountain View",
                37.386,
                -122.0838
        );
        when(geolocationService.lookup(ip)).thenReturn(mockResponse);
        ResponseEntity<GeoResponse> response = ipGeoController.lookUp(ip);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("North America", response.getBody().getContinent());
        assertEquals("United States", response.getBody().getCountry());
        assertEquals("California", response.getBody().getRegion());
        assertEquals("Mountain View", response.getBody().getCity());
        assertEquals(37.386, response.getBody().getLatitude());
        assertEquals(-122.0838, response.getBody().getLongitude());

    }

}
