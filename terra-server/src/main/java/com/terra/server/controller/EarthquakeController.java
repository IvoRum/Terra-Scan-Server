package com.terra.server.controller;

import com.terra.server.model.request.EarthquakeRequest;
import com.terra.server.model.responce.EarthquakeResponse;
import com.terra.server.service.EarthquakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/earthquake")
@RequiredArgsConstructor
public class EarthquakeController {
    private final EarthquakeService earthquakeService;

    @PostMapping("/withPosition")
    public ResponseEntity<EarthquakeResponse> getEarthqukesWithMagnitude(@RequestBody EarthquakeRequest request) {
        return ResponseEntity.ok(earthquakeService.getEarthquakes(request));
    }
}
