package com.terra.server.repository;

import com.terra.server.persistence.TerraUserLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TerraUserLogRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<TerraUserLogEntity> findLogsDateDesc() {
        Query query = new Query().limit(100).with(Sort.by(Sort.Direction.DESC, "date"));
        return mongoTemplate.find(query, TerraUserLogEntity.class);
    }
}
