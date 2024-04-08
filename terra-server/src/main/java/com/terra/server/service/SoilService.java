package com.terra.server.service;

import com.mongodb.client.MongoClients;
import com.terra.server.model.responce.dto.SoilDTO;
import com.terra.server.persistence.TerraSearchLogEntity;
import com.terra.server.persistence.TerraUserLogEntity;
import com.terra.server.repository.SoilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class SoilService {
    private final SoilRepository repository;
    private static final String CONNECTION_STRING = "mongodb+srv://ivo:ar4ebar4e@terra-log.rumnftg.mongodb.net/?retryWrites=true&w=majority&appName=terra-log";


    public List<SoilDTO> getSoidBG(){
        return repository.getTestSoil();
    }

    public List<SoilDTO> getSoilInAria(final double latitude, final double longitude, final double zoom){
        var res = repository.getSoil(latitude, longitude, zoom);
        String country = repository.getCountryOfPoint(latitude, longitude);
        if(country != null){
            MongoTemplate mongoTemplate =
                    new MongoTemplate
                            (MongoClients.create(String.format(CONNECTION_STRING)), "prod");
            var log = new TerraSearchLogEntity();
            log.setCountry(country);
            log.setDate(Date.from(Instant.now()));
            log.setSearchType("Soil");
            mongoTemplate.insert(log);
        }
        return res;
    }
}
