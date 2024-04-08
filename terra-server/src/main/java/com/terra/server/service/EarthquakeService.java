package com.terra.server.service;

import com.mongodb.client.MongoClients;
import com.terra.server.model.request.EarthquakeRequest;
import com.terra.server.model.responce.EarthquakeResponse;
import com.terra.server.persistence.TerraSearchLogEntity;
import com.terra.server.repository.EarthquakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;


@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class EarthquakeService {
    private final EarthquakeRepository repository;
    private static final String CONNECTION_STRING = "mongodb+srv://ivo:ar4ebar4e@terra-log.rumnftg.mongodb.net/?retryWrites=true&w=majority&appName=terra-log";


    public EarthquakeResponse getEarthquakes(EarthquakeRequest request) {
        var earthquakes = repository.getEarthquakes(request.getLatitude(),request.getLongitude(),request.getRadius());
        String country = repository.getCountryOfPoint(request.getLatitude(),request.getLongitude());
        if(country != null){
            MongoTemplate mongoTemplate =
                    new MongoTemplate
                            (MongoClients.create(String.format(CONNECTION_STRING)), "prod");
            var log = new TerraSearchLogEntity();
            log.setCountry(country);
            log.setDate(Date.from(Instant.now()));
            log.setSearchType("Earthquake");
            mongoTemplate.insert(log);
        }
        return EarthquakeResponse.builder()
                .allEarthquakesInArea(earthquakes)
                .build();
    }
}
