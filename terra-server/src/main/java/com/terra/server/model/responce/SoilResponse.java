package com.terra.server.model.responce;

import com.terra.server.model.responce.dto.SoilDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SoilResponse {
    private List<SoilDTO> soilRecords;
}
