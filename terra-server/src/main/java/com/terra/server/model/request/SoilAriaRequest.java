package com.terra.server.model.request;

import lombok.Data;

@Data
public class SoilAriaRequest {
    private double latitude;
    private double longitude;
    private double zoom;
}
