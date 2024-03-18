package com.terra.server.service;

import com.terra.server.model.request.EarthquakeRequest;
import com.terra.server.model.responce.EarthquakeDTO;
import com.terra.server.model.responce.EarthquakeResponse;
import com.terra.server.repository.EarthquakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class EarthquakeService {
    private EarthquakeRepository repository;

    public EarthquakeResponse getEarthquakes(EarthquakeRequest request) throws SQLException {
        return EarthquakeResponse.builder()
                .allEarthquakesInArea(repository.getEarthquakes().orElse(null))
                .build();
    }
}
