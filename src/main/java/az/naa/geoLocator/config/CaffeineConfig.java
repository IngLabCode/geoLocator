package az.naa.geoLocator.config;

import az.naa.geoLocator.dto.GeoResponse;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CaffeineConfig {
    @Bean
    public Cache<String, GeoResponse> geoResponseCacheCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofDays(30))
                .maximumSize(100_000)
                .recordStats()
                .build();
    }
}
