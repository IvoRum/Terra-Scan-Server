package com.terra.server.model.responce;

import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class SoilDTO {
    private float lon;
    private float lat;
}
