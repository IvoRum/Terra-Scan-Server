package com.terra.server.service;

import com.terra.server.model.request.AuthenticationRequest;
import com.terra.server.model.responce.AuthenticationResponse;
import com.terra.server.persistence.User;
import com.terra.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    public ResponseEntity<AuthenticationResponse> logInUser(AuthenticationRequest request) {

        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if(user.isEmpty()){
            //no user registered with sent email or password not matching
            return new ResponseEntity<>(AuthenticationResponse.builder()
                    .accessToken(null)
                    .build(), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(authenticationService.authenticate(
                new AuthenticationRequest(request.getEmail(), request.getPassword())), HttpStatus.ACCEPTED);
    }
}
