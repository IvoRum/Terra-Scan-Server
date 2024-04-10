package com.terra.server.model.responce;

import com.terra.server.model.responce.dto.LoginDataDTO;
import lombok.Data;

import java.util.List;

@Data
public class LoginDataResponse {
    private Integer page;
    private List<LoginDataDTO> data;
}
