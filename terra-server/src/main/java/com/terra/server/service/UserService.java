package com.terra.server.service;

import com.terra.server.model.responce.UserDataResponse;
import com.terra.server.model.responce.UserModel;
import com.terra.server.persistence.User;
import com.terra.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDataResponse getUserData(final String email) {
        User userData = userRepository.findByEmail(email).orElseThrow();
        return new UserDataResponse(userData.getFirstname(), userData.getLastname(), userData.getEmail());
    }
}
