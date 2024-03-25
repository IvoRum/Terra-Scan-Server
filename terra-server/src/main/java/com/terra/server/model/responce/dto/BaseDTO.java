package com.terra.server.model.responce.dto;

import com.terra.server.model.PolygonPoint;
import lombok.Data;

import java.util.List;
@Data
public abstract class BaseDTO {
    private List<PolygonPoint> coordinates;
}
