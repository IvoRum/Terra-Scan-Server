package com.terra.server.controller;

import com.terra.server.model.request.SoilAriaRequest;
import com.terra.server.model.responce.dto.SoilDTO;
import com.terra.server.service.SoilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/soil")
@RequiredArgsConstructor
public class SoilController {
    private final SoilService soilService;

    @GetMapping("/test")
    public ResponseEntity<List<SoilDTO>> getTestSoil() {
        return ResponseEntity.ok(soilService.getSoidBG());
    }

    @PostMapping
    public ResponseEntity<List<SoilDTO>> getSoilInAria(@RequestBody SoilAriaRequest soilAriaRequest) {
        return ResponseEntity.ok(
                soilService.getSoilInAria(soilAriaRequest.getLatitude(), soilAriaRequest.getLongitude(), soilAriaRequest.getZoom()));
    }
}
