package az.naa.geoLocator;

import az.naa.geoLocator.dto.GeoResponse;
import az.naa.geoLocator.service.impl.GeolocationService;
import az.naa.geoLocator.service.impl.RateLimitedProviderGateway;
import com.github.benmanes.caffeine.cache.Cache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GeolocationServiceTest {

    private GeolocationService geolocationService;
    private RateLimitedProviderGateway gateway;
    private Cache<String, GeoResponse> cache;

    @BeforeEach
    void setUp() {
        gateway = mock(RateLimitedProviderGateway.class);
        cache = mock(Cache.class);
        geolocationService = new GeolocationService(cache, gateway);
    }

    @Test
    void testValidIPv6() {
        String ip = "2001:0db8:85a3:0000:0000:8a2e:0370:7334";
        GeoResponse mockResponse = new GeoResponse(
                "North America",
                "United States",
                "California",
                "Mountain View",
                37.386,
                -122.0838);
        when(cache.getIfPresent(ip)).thenReturn(null);
        when(gateway.fetch(ip)).thenReturn(mockResponse);

        GeoResponse result = geolocationService.lookup(ip);

        assertEquals(mockResponse, result);
        verify(cache).put(ip, mockResponse);
        verify(gateway).fetch(ip);
    }

    @Test
    void testValidIPv4() {
        String ip = "8.8.8.8";
        GeoResponse mockResponse = new GeoResponse(
                "North America",
                "United States",
                "California",
                "Mountain View",
                37.386,
                -122.0838);
        when(cache.getIfPresent(ip)).thenReturn(null);
        when(gateway.fetch(ip)).thenReturn(mockResponse);

        GeoResponse result = geolocationService.lookup(ip);

        assertEquals(mockResponse, result);
        verify(cache).put(ip, mockResponse);
        verify(gateway).fetch(ip);
    }

}
