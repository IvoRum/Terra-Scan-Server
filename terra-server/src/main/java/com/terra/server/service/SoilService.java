package com.terra.server.service;

import com.terra.server.loggers.SoilLogger;
import com.terra.server.model.responce.dto.SoilDTO;
import com.terra.server.repository.SoilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class SoilService {
    private final SoilRepository repository;

    public List<SoilDTO> getSoidBG(){
        return repository.getTestSoil();
    }

    public List<SoilDTO> getSoilInAria(final double latitude, final double longitude, final double zoom){
        var res = repository.getSoil(latitude, longitude, zoom);
        String country = repository.getCountryOfPoint(latitude, longitude);
        if(country != null){
            new SoilLogger(country).start();
        }
        return res;
    }
}
