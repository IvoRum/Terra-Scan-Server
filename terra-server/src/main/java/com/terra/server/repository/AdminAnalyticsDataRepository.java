package com.terra.server.repository;

import com.terra.server.model.responce.dto.LoginDataDTO;
import com.terra.server.persistence.TerraUserLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

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
}
