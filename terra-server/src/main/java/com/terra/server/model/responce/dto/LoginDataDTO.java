package com.terra.server.model.responce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDataDTO {
    private String userEmail;
    private String token;
    private String ipAddress;
    private String macAddress;
    private String date;
}
