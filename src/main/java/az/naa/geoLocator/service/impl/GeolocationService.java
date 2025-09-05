package az.naa.geoLocator.service.impl;

import az.naa.geoLocator.dto.GeoResponse;
import az.naa.geoLocator.dto.IpAddress;
import com.github.benmanes.caffeine.cache.Cache;
import az.naa.geoLocator.exception.ProviderException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class GeolocationService {

    private final Cache<String, GeoResponse> cache;
    private final RateLimitedProviderGateway gateway;

    private static final Pattern IPV4_PATTERN = Pattern.compile(
            "^((25[0-5]|2[0-4][0-9]|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?\\d\\d?)$"
    );
    private static final Pattern IPV6_PATTERN = Pattern.compile(
            "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}"
    );


    public GeoResponse lookup(String rawIp) {
        IpAddress ip = IpAddress.of(rawIp);

        if (!isValidIp(ip.getValue())) {
            throw new ProviderException("Invalid IP address:" + ip.getValue());
        }

        GeoResponse cached = cache.getIfPresent(ip.getValue());
        if (cached != null) return cached;

        GeoResponse geoLocation = gateway.fetch(ip.getValue());
        cache.put(ip.getValue(), geoLocation);
        return geoLocation;
    }


    private boolean isValidIp(String ip) {
        return IPV4_PATTERN.matcher(ip).matches() || IPV6_PATTERN.matcher(ip).matches();
    }
}
