package az.naa.geoLocator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GeoLocatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeoLocatorApplication.class, args);
    }

}
