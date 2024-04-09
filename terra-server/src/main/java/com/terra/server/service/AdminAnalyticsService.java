package com.terra.server.service;

import com.terra.server.model.responce.dto.LoginDataDTO;
import com.terra.server.model.responce.dto.SearchDataDTO;
import com.terra.server.repository.AdminAnalyticsDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class AdminAnalyticsService {
    private final AdminAnalyticsDataRepository logRepository;

    public List<LoginDataDTO> getLoginData(int page) {
        return logRepository.findLogsDateDesc((page - 1) * 100);
    }

    public List<SearchDataDTO> getSearchData(int page, String searchType) {
        return logRepository.findSearchLogsDateDesc(1,searchType);
    }
}
