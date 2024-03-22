package com.terra.server.model.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDataResponse {
    private String firstName;
    private String lastName;
    private String email;
}
