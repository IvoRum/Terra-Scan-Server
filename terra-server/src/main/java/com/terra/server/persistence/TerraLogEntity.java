package com.terra.server.persistence;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "terra_log")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TerraLogEntity {
    @MongoId
    private ObjectId id;
    private String userEmail;
    private String token;
}
