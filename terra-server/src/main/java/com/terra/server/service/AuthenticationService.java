package com.terra.server.service;

import com.terra.server.loggers.LoginActivityLogger;
import com.terra.server.model.request.AuthenticationRequest;
import com.terra.server.model.responce.AuthenticationResponse;
import com.terra.server.repository.UserRepository;
import com.terra.server.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.terra.server.jwt.JwtUtil;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "user not found"
                )
        );

        if(user.getAuthorities().contains(new SimpleGrantedAuthority(Role.BANNED.name()))){
            return AuthenticationResponse.builder()
                    .accessToken(null)
                    .role(user.getRole().toString())
                    .build();
        }

        var jwtToken = jwtUtil.generateToken(user);
        new LoginActivityLogger(jwtToken,request.getEmail(),request.getIpAddress(),request.getMacAddress()).start();
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .role(user.getRole().toString())
                .build();
    }
}