package controller;

import dto.GeocodeResponse;
import integration.OpenGovernmentCZIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ServiceController.class);
    @Autowired
    private OpenGovernmentCZIntegration openGovernmentCZIntegration;

    @GetMapping("/api")
    public String defaultMethod() {
        return "Called";
    }

    @GetMapping(path = "/location/{locationName}")
    public ResponseEntity<GeocodeResponse> getLocationInfo(@PathVariable String locationName) {
        log.info("getLocationInfo() was called");
        return ResponseEntity.ok()
                .header("id", "1234455")
                .body(openGovernmentCZIntegration.getGeocodeInfo(locationName));
    }
}
