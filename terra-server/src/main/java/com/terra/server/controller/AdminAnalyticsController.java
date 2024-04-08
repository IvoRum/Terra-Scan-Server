package com.terra.server.controller;


import com.terra.server.model.request.LoginDataRequest;
import com.terra.server.model.responce.dto.LoginDataDTO;
import com.terra.server.service.AdminAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminAnalyticsController {
    private final AdminAnalyticsService adminAnalyticsService;

    @PostMapping("/loginData")
    public ResponseEntity<List<LoginDataDTO>> getTestLogs(@RequestBody LoginDataRequest loginDataRequest) {
        return ResponseEntity.ok(adminAnalyticsService.getLoginData(loginDataRequest.getPage() != null? loginDataRequest.getPage() : 1));
    }
}
