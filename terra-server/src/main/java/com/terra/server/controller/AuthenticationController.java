package com.terra.server.controller;

import com.terra.server.model.request.AuthenticationRequest;
import com.terra.server.model.request.RegistrationRequest;
import com.terra.server.model.responce.AuthenticationResponse;
import com.terra.server.service.AuthenticationService;
import com.terra.server.service.LoginService;
import com.terra.server.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vi/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final RegisterService registerService;
    private final LoginService loginService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(
            @RequestBody RegistrationRequest request
    ) {
        AuthenticationResponse response = registerService.registerUser(request);
        if(response == null){
            return ResponseEntity.status(HttpStatus.SEE_OTHER).body(null);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logIn")
    public ResponseEntity<AuthenticationResponse> logInUser(
            @RequestBody AuthenticationRequest request
    ) {
        return loginService.logInUser(request);
    }

}