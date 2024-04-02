package com.terra.server.service;

import com.mongodb.client.MongoClients;
import com.terra.server.model.responce.dto.SoilDTO;
import com.terra.server.persistence.TerraLogEntity;
import com.terra.server.repository.SoilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class SoilService {
    private final SoilRepository repository;
    private static final String CONNECTION_STRING = "mongodb+srv://ivo:ar4ebar4e@terra-log.rumnftg.mongodb.net/?retryWrites=true&w=majority&appName=terra-log";


    public List<SoilDTO> getSoidBG(){
        MongoTemplate mongoTemplate =
                new MongoTemplate
                        (MongoClients.create(String.format(CONNECTION_STRING)), "prod");
        var log = new TerraLogEntity();
        log.setToken("123");
        log.setUserEmail("dgg");
        mongoTemplate.insert(log);
        return repository.getTestSoil();
    }

    public List<SoilDTO> getSoilInAria(final double latitude, final double longitude, final double zoom){
        return repository.getSoil(latitude, longitude, zoom);
    }
}
