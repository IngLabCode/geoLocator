package az.naa.geoLocator;

import az.naa.geoLocator.dto.GeoResponse;
import az.naa.geoLocator.exception.RateLimitException;
import az.naa.geoLocator.service.GeolocationProvider;
import az.naa.geoLocator.service.impl.RateLimitedProviderGateway;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RateLimitedProviderGatewayTest {

    @Test
    void testFetchSuccess() {
        GeolocationProvider provider = mock(GeolocationProvider.class);
        GeoResponse expected = new GeoResponse( "North America" ,
                "United States",
                "California",
                "Mountain View",
                37.4223,
                -122.085);
        when(provider.getByIp("8.8.8.8")).thenReturn(expected);

        RateLimitedProviderGateway gateway = new RateLimitedProviderGateway(provider);

        GeoResponse result = gateway.fetch("8.8.8.8");

        assertEquals(expected, result);
        verify(provider, times(1)).getByIp("8.8.8.8");
    }

    @Test
    void testFetchProviderThrowsException() {
        GeolocationProvider provider = mock(GeolocationProvider.class);
        when(provider.getByIp("2.2.2.2")).thenThrow(new RuntimeException("Provider failed"));

        RateLimitedProviderGateway gateway = new RateLimitedProviderGateway(provider);

        assertThrows(RateLimitException.class, () -> gateway.fetch("2.2.2.2"));
        verify(provider, times(1)).getByIp("2.2.2.2");
    }

    @Test
    void testFetchTimeout() {
        GeolocationProvider provider = mock(GeolocationProvider.class);
        when(provider.getByIp("8.8.8.8")).thenAnswer(invocation -> {
            Thread.sleep(35000);
            return new GeoResponse(
                    "North America" ,
                    "United States",
                    "California",
                    "Mountain View",
                    37.4223,
                    -122.085);
        });

        RateLimitedProviderGateway gateway = new RateLimitedProviderGateway(provider);

        assertThrows(RateLimitException.class, () -> gateway.fetch("8.8.8.8"));
        verify(provider, times(1)).getByIp("8.8.8.8");
    }
}
