package com.terra.server.model.responce;

import com.terra.server.model.responce.dto.EarthquakeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EarthquakeResponse {
    private List<EarthquakeDTO> allEarthquakesInArea;
}