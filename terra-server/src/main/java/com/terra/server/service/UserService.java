package com.terra.server.service;

import com.terra.server.model.responce.UserDataResponse;
import com.terra.server.persistence.User;
import com.terra.server.repository.UserRepository;
import com.terra.server.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

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

    public List<UserDataResponse> getAllUsersData() {
        List<User> allUsers = userRepository.findAll();
        List<UserDataResponse> userDataResponses = new ArrayList<>();
        allUsers.forEach(user -> {
            if (user.getRole() != Role.SUPERADMIN) {
                if (user.getRole() != Role.BANNED) {
                    userDataResponses.add(new UserDataResponse(user.getFirstname(), user.getLastname(), user.getEmail()));
                }
            }
        });
        return userDataResponses;
    }

    public boolean makeUserAdmin(String userEmail) {
        try {
            User userData = userRepository.findByEmail(userEmail).orElseThrow();
            userData.setRole(Role.ADMIN);
            userRepository.save(userData);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    public boolean banUser(String userEmail) {
        try {
            User userData = userRepository.findByEmail(userEmail).orElseThrow();
            userData.setRole(Role.BANNED);
            userRepository.save(userData);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    public boolean unbanUser(String userEmail) {
        try {
            User userData = userRepository.findByEmail(userEmail).orElseThrow();
            userData.setRole(Role.USER);
            userRepository.save(userData);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }
}
