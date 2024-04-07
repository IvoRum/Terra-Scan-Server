package com.terra.server.service;

import com.terra.server.model.responce.dto.SoilDTO;
import com.terra.server.persistence.TerraUserLogEntity;
import com.terra.server.repository.TerraUserLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class LogService {
    private final TerraUserLogRepository logRepository;


    public List<TerraUserLogEntity> getTestLog() {
        return logRepository.findLogsDateDesc();
    }
}
