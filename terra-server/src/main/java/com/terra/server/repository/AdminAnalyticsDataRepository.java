package com.terra.server.repository;

import com.terra.server.model.responce.dto.LoginDataDTO;
import com.terra.server.model.responce.dto.SearchDataDTO;
import com.terra.server.model.responce.dto.SearchDataDoc;
import com.terra.server.persistence.TerraUserLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.crypto.Cipher;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminAnalyticsDataRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<LoginDataDTO> findLogsDateDesc(int startRow) {
        Query query = new Query().skip(startRow).limit(100).with(Sort.by(Sort.Direction.DESC, "date"));
        var docs =  mongoTemplate.find(query, TerraUserLogEntity.class);
        var res = new ArrayList<LoginDataDTO>();
        for (var doc : docs) {
            res.add(new LoginDataDTO(doc.getUserEmail(),doc.getToken(),doc.getIpAddress(),doc.getMacAddress(),doc.getDate().toString()));
        }
        return res;
    }

    public List<LoginDataDTO> findLogsDateByEmail(String email) {
        Query query = new Query().addCriteria(Criteria.where("userEmail").is(email));
        var docs =  mongoTemplate.find(query, TerraUserLogEntity.class);
        var res = new ArrayList<LoginDataDTO>();
        for (var doc : docs) {
            res.add(new LoginDataDTO(doc.getUserEmail(),doc.getToken(),doc.getIpAddress(),doc.getMacAddress(),doc.getDate().toString()));
        }
        return res;
    }

    public List<SearchDataDTO> findSearchLogsDateDesc(int startRow, String SearchType) {
        Aggregation agg = Aggregation.newAggregation(Aggregation.group("country","searchType").count().as("count"));
        var res = mongoTemplate.aggregate(agg,"terra_search_log", SearchDataDoc.class);
        var toReturn = new ArrayList<SearchDataDTO>();
        for(SearchDataDoc doc : res) {
            if(doc.getSearchType().equals(SearchType)){
                var dto = new SearchDataDTO();
                dto.setCountry(doc.getCountry());
                dto.setSearchType(doc.getSearchType());
                dto.setNUmberOfSearches(doc.getCount());
                toReturn.add(dto);
            }
        }
        return toReturn;
    }
}
