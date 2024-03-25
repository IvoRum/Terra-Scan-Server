package com.terra.server.model.responce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoilDTO extends BaseDTO{
    private Integer soilNumber;
    private String soilType;
}
