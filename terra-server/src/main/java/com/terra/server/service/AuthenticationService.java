package com.terra.server.service;

import com.mongodb.client.MongoClients;
import com.terra.server.model.request.AuthenticationRequest;
import com.terra.server.model.responce.AuthenticationResponse;
import com.terra.server.persistence.TerraUserLogEntity;
import com.terra.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.terra.server.jwt.JwtUtil;

import java.sql.Date;
import java.time.Instant;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private static final String CONNECTION_STRING = "mongodb+srv://ivo:ar4ebar4e@terra-log.rumnftg.mongodb.net/?retryWrites=true&w=majority&appName=terra-log";


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

        var jwtToken = jwtUtil.generateToken(user);
        MongoTemplate mongoTemplate =
                new MongoTemplate
                        (MongoClients.create(String.format(CONNECTION_STRING)), "prod");
        var log = new TerraUserLogEntity();
        log.setToken(jwtToken);
        log.setUserEmail(request.getEmail());
        log.setIpAddress(request.getIpAddress());
        log.setMacAddress(request.getMacAddress());
        log.setDate(Date.from(Instant.now()));
        mongoTemplate.insert(log);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}