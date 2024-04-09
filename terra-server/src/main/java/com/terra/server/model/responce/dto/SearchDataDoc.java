package com.terra.server.model.responce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDataDoc {
    private Id _id;
    private int count;

    public String getCountry(){
        return _id.getCountry();
    }
    public String getSearchType(){
        return _id.getSearchType();
    }
}
@Document
@Data
class Id{
    private String country;
    private String searchType;
}
