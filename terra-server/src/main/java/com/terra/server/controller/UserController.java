package com.terra.server.controller;

import com.terra.server.model.request.AuthenticationRequest;
import com.terra.server.model.request.UserLogDataRequest;
import com.terra.server.model.responce.UserDataResponse;
import com.terra.server.model.responce.dto.LoginDataDTO;
import com.terra.server.service.AdminAnalyticsService;
import com.terra.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AdminAnalyticsService adminAnalyticsService;

    @GetMapping
    public ResponseEntity<UserDataResponse> getUserData(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        return ResponseEntity.ok(userService.getUserData(authentication.getName()));//In this case the name is the email of the use
    }

    @GetMapping("/super/all")
    public ResponseEntity<List<UserDataResponse>> getAllUserData(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        return ResponseEntity.ok(userService.getAllUsersData());
    }

    @PostMapping("/super/user")
    public ResponseEntity<List<LoginDataDTO>> getAllUserlogData(
            @CurrentSecurityContext(expression = "authentication") Authentication authentication,
            @RequestBody UserLogDataRequest request
    ) {
        return ResponseEntity.ok(adminAnalyticsService.getAllLogDataForUser(request.getEmail()));
    }
}
