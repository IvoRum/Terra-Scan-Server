package com.terra.server.controller;

import com.terra.server.model.request.AuthenticationRequest;
import com.terra.server.model.request.RegistrationRequest;
import com.terra.server.model.responce.AuthenticationResponse;
import com.terra.server.service.AuthenticationService;
import com.terra.server.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final RegisterService registerService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request,
            @RequestHeader("Host") String remoteAddress
    ) {
        request.setIpAddress(remoteAddress);
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(
            @RequestBody RegistrationRequest request,
            @RequestHeader("Host") String remoteAddress
    ) {
        request.setIpAddress(remoteAddress);
        AuthenticationResponse response = registerService.registerUser(request);
        if(response == null){
            return ResponseEntity.status(HttpStatus.SEE_OTHER).body(null);
        }
        return ResponseEntity.ok(response);
    }

}