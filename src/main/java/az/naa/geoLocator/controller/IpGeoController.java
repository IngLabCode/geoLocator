package az.naa.geoLocator.controller;

import az.naa.geoLocator.dto.GeoResponse;
import az.naa.geoLocator.service.impl.GeolocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ip")
public class IpGeoController {
    private final GeolocationService service;

    public IpGeoController(GeolocationService service) {
        this.service = service;
    }

    @GetMapping("/{ip}")
    public ResponseEntity<GeoResponse> lookUp(@PathVariable String ip) {
        GeoResponse geoResponse = service.lookup(ip);
        return ResponseEntity.ok(geoResponse);
    }
}
