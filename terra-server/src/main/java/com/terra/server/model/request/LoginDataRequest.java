package com.terra.server.model.request;

import lombok.Data;

import java.sql.Date;

@Data
public class LoginDataRequest {
    private Date dateFrom;
    private Date dateTo;
    private String searchString;
    private Integer page;
}
