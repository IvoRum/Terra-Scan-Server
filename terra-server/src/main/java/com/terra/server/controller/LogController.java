package com.terra.server.controller;


import com.terra.server.model.responce.dto.SoilDTO;
import com.terra.server.persistence.TerraUserLogEntity;
import com.terra.server.service.LogService;
import com.terra.server.service.SoilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/log")
@RequiredArgsConstructor
public class LogController {
    private final LogService logService;

    @GetMapping("/test")
    public ResponseEntity<List<TerraUserLogEntity>> getTestSoil() {
        return ResponseEntity.ok(logService.getTestLog());
    }
}
