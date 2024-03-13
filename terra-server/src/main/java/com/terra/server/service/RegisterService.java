package com.terra.server.service;

import com.terra.server.config.SecurityConfig;
import com.terra.server.model.request.AuthenticationRequest;
import com.terra.server.model.responce.AuthenticationResponse;
import com.terra.server.persistence.User;
import com.terra.server.repository.UserRepository;
import com.terra.server.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.terra.server.model.request.RegistrationRequest;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final SecurityConfig securityConfig;
    public AuthenticationResponse registerUser(RegistrationRequest request) {

        var user = userRepository.findByEmail(request.getEmail());
        if(user.isPresent()){
            //email already taken
            return null;
        }

        User toRegister = new User();
        toRegister.setEmail(request.getEmail());
        toRegister.setPassword(securityConfig.passwordEncoder().encode(request.getPassword()));
        toRegister.setFirstname(request.getFirstName());
        toRegister.setLastname(request.getLastName());
        toRegister.setRole(Role.USER);
        userRepository.save(toRegister);

        return authenticationService.authenticate(
                new AuthenticationRequest(request.getEmail(),request.getPassword()));
    }
}
