package com.terra.server.model.request;

import lombok.Data;

import java.util.Date;

@Data
public class SearchDataRequest {
    private String searchType;
    private Integer page;
}
