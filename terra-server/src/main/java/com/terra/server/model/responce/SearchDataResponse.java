package com.terra.server.model.responce;

import com.terra.server.model.responce.dto.SearchDataDTO;
import lombok.Data;

import java.util.List;
@Data
public class SearchDataResponse {
    private Integer page;
    private List<SearchDataDTO> data;
}
