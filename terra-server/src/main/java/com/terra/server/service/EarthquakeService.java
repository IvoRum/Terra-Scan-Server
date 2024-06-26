package com.terra.server.service;

import com.terra.server.loggers.EarthquakeLogger;
import com.terra.server.model.request.EarthquakeRequest;
import com.terra.server.model.responce.EarthquakeResponse;
import com.terra.server.repository.EarthquakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class EarthquakeService {
    private final EarthquakeRepository repository;

    public EarthquakeResponse getEarthquakes(EarthquakeRequest request) {
        var earthquakes = repository.getEarthquakes(request.getLatitude(),request.getLongitude(),request.getRadius());
        String country = repository.getCountryOfPoint(request.getLatitude(),request.getLongitude());
        if(country != null){
            new EarthquakeLogger(country).start();
        }
        return EarthquakeResponse.builder()
                .allEarthquakesInArea(earthquakes)
                .build();
    }
}
