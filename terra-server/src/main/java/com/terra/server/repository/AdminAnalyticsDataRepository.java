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

import java.util.*;

@Repository
public class AdminAnalyticsDataRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<LoginDataDTO> findLogsDateDesc(int startRow, Date from, Date to) {
        Query query = new Query().skip(startRow).limit(100).with(Sort.by(Sort.Direction.DESC, "date"));
        query.addCriteria(Criteria.where("date").gt(from).lt(to));
        return getLoginDataDTOS(query);
    }

    public List<LoginDataDTO> searchBySingleKeyWord(String keyword, Date from, Date to) {
        String keyRegex = ".*" + keyword + ".*";
        Criteria criteria = new Criteria();
        criteria = criteria.orOperator(Criteria.where("userEmail").regex(keyRegex),
                Criteria.where("token").regex(keyRegex),
                Criteria.where("ipAddress").regex(keyRegex),
                Criteria.where("macAddress").regex(keyRegex));
        Query query = new Query().addCriteria(criteria);
        query.addCriteria(Criteria.where("date").gt(from).lt(to));
        return getLoginDataDTOS(query);
    }

    public List<LoginDataDTO> searchKeyValuePair(Map<String,String> pairs, Date from, Date to) {
        List<Criteria> criteriaList = new ArrayList<>();
        for (String key : pairs.keySet()) {
            String keyRegex = ".*" + pairs.get(key) + ".*";
            criteriaList.add(Criteria.where(key).regex(keyRegex));
        }
        Criteria criteria = new Criteria().orOperator(criteriaList);
        Query query = new Query().addCriteria(criteria);
        query.addCriteria(Criteria.where("date").gt(from).lt(to));
        return getLoginDataDTOS(query);
    }

    public List<LoginDataDTO> findLogsDateByEmail(String email) {
        Query query = new Query().addCriteria(Criteria.where("userEmail").is(email));
        return getLoginDataDTOS(query);
    }

    private List<LoginDataDTO> getLoginDataDTOS(Query query) {
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
