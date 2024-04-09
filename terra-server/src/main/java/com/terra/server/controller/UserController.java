package com.terra.server.controller;

import com.terra.server.model.request.UserChangeStatusRequest;
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



    @PostMapping("super/makeUserAdmin")
    public ResponseEntity<String> makeUserAdmin(@RequestBody UserChangeStatusRequest request){
        if(userService.makeUserAdmin(request.getUserEmail())){
            return ResponseEntity.ok("Successfully made admin " + request.getUserEmail());
        } else {
            return ResponseEntity.internalServerError().body("Error has occured.");
        }
    }

    @PostMapping("super/banUser")
    public ResponseEntity<String> banUser(@RequestBody UserChangeStatusRequest request){
        if(userService.banUser(request.getUserEmail())){
            return ResponseEntity.ok("Successfully banned " + request.getUserEmail());
        } else {
            return ResponseEntity.internalServerError().body("Error has occured.");
        }
    }
    @PostMapping("super/unBanUser")
    public ResponseEntity<String> unBanUser(@RequestBody UserChangeStatusRequest request){
        if(userService.unbanUser(request.getUserEmail())){
            return ResponseEntity.ok("Successfully unbanned " + request.getUserEmail());
        } else {
            return ResponseEntity.internalServerError().body("Error has occured.");
        }
    }
}
