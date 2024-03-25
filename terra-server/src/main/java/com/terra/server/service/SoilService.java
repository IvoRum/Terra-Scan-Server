package com.terra.server.service;

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
    private final SoilRepository repositoryPOC;

    public List<SoilDTO> getSoidBG(){
        return repositoryPOC.getSoil();
    }
}
