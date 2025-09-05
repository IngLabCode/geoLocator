package az.naa.geoLocator;

import az.naa.geoLocator.dto.GeoResponse;
import az.naa.geoLocator.dto.IpApiDto;
import az.naa.geoLocator.service.impl.IpApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.security.ProviderException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class IpApiClientTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private IpApiClient ipApiClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ipApiClient = new IpApiClient(restTemplate);
        ReflectionTestUtils.setField(ipApiClient, "URL_TEMPLATE", "https://freeipapi.com/api/json/%s");

    }

    @Test
    void testGetByIp_Success() {
        IpApiDto dto = new IpApiDto(
                null,
                "North America",
                "United States",
                "California",
                "Mountain View",
                37.386,
                -122.0838
        );
        when(restTemplate.getForEntity(anyString(), eq(IpApiDto.class)))
                .thenReturn(ResponseEntity.ok(dto));

        GeoResponse geoResponse = ipApiClient.getByIp("8.8.8.8");

        assertNotNull(geoResponse);
        assertEquals("North America", geoResponse.getContinent());
        assertEquals("United States", geoResponse.getCountry());
        assertEquals(37.386, geoResponse.getLatitude());
        assertEquals(-122.0838, geoResponse.getLongitude());
    }

    @Test
    void testGetByIp_ApiError() {
        IpApiDto dto = new IpApiDto(false,
                "North America",
                "United States",
                "California",
                "Mountain View",
                37.386,
                -122.0838);
        when(restTemplate.getForEntity(anyString(), eq(IpApiDto.class)))
                .thenReturn(ResponseEntity.ok(dto));

        assertThrows(ProviderException.class, () -> ipApiClient.getByIp("8.8.8.8"));
    }

}