package com.terra.server.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document(collection = "terra_search_log")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TerraSearchLogEntity {
    @MongoId
    private ObjectId id;
    private String country;
    private Date date;
    private String searchType;
}
