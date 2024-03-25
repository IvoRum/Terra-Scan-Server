package com.terra.server.controller;

import com.terra.server.model.responce.dto.SoilDTO;
import com.terra.server.service.SoilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/soil")
@RequiredArgsConstructor
public class SoilController {
    private final SoilService soilService;
    @GetMapping
    public ResponseEntity<List<SoilDTO>> check(){
        return ResponseEntity.ok(soilService.getSoidBG());
    }

    @PostMapping
    public ResponseEntity<List<SoilDTO>> aria(){
        return ResponseEntity.ok(soilService.getSoidBG());
    }
}
