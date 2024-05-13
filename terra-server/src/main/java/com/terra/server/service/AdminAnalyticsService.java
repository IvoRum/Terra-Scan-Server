package com.terra.server.service;

import com.terra.server.model.responce.LoginDataResponse;
import com.terra.server.model.responce.dto.LoginDataDTO;
import com.terra.server.model.responce.dto.SearchDataDTO;
import com.terra.server.repository.AdminAnalyticsDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class AdminAnalyticsService {
    private final AdminAnalyticsDataRepository logRepository;
    private static final Set<String> validKeys = Set.of("userEmail","token","ipAddress","macAddress");

    public LoginDataResponse getLoginData(int page, Date dateFrom, Date dateTo, String searchString) {
        if(dateTo == null){
            dateTo = Date.valueOf(LocalDate.now());
        }
        if(dateFrom == null){
            dateFrom = Date.valueOf(LocalDate.now().minus(90, ChronoUnit.DAYS));
        }
        LoginDataResponse response = new LoginDataResponse();
        response.setPage(page);
        if(TimeUnit.DAYS.convert(dateTo.getTime() - dateFrom.getTime(),TimeUnit.MILLISECONDS) > 90){
            response.setErrorText("Timeframe of report cannot exceed 3 months.");
            return response;
        }
        if(searchString == null){
            response.setData(logRepository.findLogsDateDesc((page - 1) * 100,dateFrom,dateTo));
            return response;
        }
        if(searchString.startsWith("\"") && searchString.endsWith("\"")){
            //whole word search in all fields

            response.setData(logRepository.searchBySingleKeyWord(searchString.substring(1,searchString.length()-1),dateFrom,dateTo));
        } else if(searchString.matches("([a-z,A-Z]+:[A-Z,a-z,\\.,0-9,@]+ ?)+")) {
            //key value pair search
            try{
                response.setData(logRepository.searchKeyValuePair(scrapeSearchString(searchString),dateFrom,dateTo));
            } catch (RuntimeException e){
                response.setErrorText("Unrecognized Keyword in search request.");
                return response;
            }
        } else {
            Set<LoginDataDTO> data = new HashSet<>();
            for(String keyword : searchString.split(" ")){
                data.addAll(logRepository.searchBySingleKeyWord(keyword,dateFrom,dateTo));
            }
            response.setData(data.stream().toList());
        }
        return response;
    }

    public List<LoginDataDTO> getAllLogDataForUser(String email) {
        return logRepository.findLogsDateByEmail(email);
    }

    public List<SearchDataDTO> getSearchData(int page, String searchType) {
        return logRepository.findSearchLogsDateDesc(1,searchType);
    }

    private Map<String,String> scrapeSearchString(String SearchString){
        Map<String,String> res = new HashMap<>();
        for(String pair : SearchString.split(" ")){
            String[] keyValue = pair.split(":");
            if(!validKeys.contains(keyValue[0])){
                throw new RuntimeException();
            }
            res.put(keyValue[0],keyValue[1]);
        }
        return res;
    }
}