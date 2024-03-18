package com.terra.server.service;

import com.terra.server.repository.SoilRepositoryPOC;
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
    private final SoilRepositoryPOC repositoryPOC;

    public List<String> getSoidBG(){
        return repositoryPOC.getSoil();
    }
}
