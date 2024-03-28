package com.terra.server.init;

import com.terra.server.persistence.User;
import com.terra.server.repository.UserRepository;
import com.terra.server.type.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setEmail("ivoUser@mail.com");
        user.setPassword(passwordEncoder.encode("12345678"));
        user.setRole(Role.USER);
        user.setFirstname("Ivo");
        userRepository.save(user);

        User userAdmin = new User();
        userAdmin.setEmail("ivoAdmin@mail.com");
        userAdmin.setPassword(passwordEncoder.encode("12345678"));
        userAdmin.setRole(Role.ADMIN);
        userAdmin.setFirstname("IvoAdmin");
        userRepository.save(userAdmin);

        User userSuperAdmin = new User();
        userSuperAdmin.setEmail("ivoSuperAdmin@mail.com");
        userSuperAdmin.setPassword(passwordEncoder.encode("12345678"));
        userSuperAdmin.setRole(Role.SUPERADMIN);
        userSuperAdmin.setFirstname("IvoSupperAdmin");
        userRepository.save(userSuperAdmin);
    }
}
