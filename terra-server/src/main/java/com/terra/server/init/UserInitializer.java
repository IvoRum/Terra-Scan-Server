package com.terra.server.init;

import com.terra.server.persistence.TerraUserEntity;
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
        TerraUserEntity user = new TerraUserEntity();
        user.setEmail("ivoAdmin@mail.com");
        user.setPassword(passwordEncoder.encode("12345678"));
        user.setRole(Role.USER);
        user.setFirstName("Ivaylo");
        user.setFamilyName("Rumenov");
        user.setPhone("0892332123");
        user.setId(1l);
        userRepository.save(user);

        TerraUserEntity user1 = new TerraUserEntity();
        user1.setEmail("nikiAdmin@mail.com");
        user1.setPassword(passwordEncoder.encode("12345678"));
        user1.setRole(Role.USER);
        user1.setFirstName("Niki");
        user1.setFamilyName("Staykov");
        user1.setPhone("089111111");
        user1.setId(2l);
        userRepository.save(user1);

        TerraUserEntity user2 = new TerraUserEntity();
        user2.setEmail("niki2Admin@mail.com");
        user2.setPassword(passwordEncoder.encode("12345678"));
        user2.setRole(Role.USER);
        user2.setFirstName("Niki");
        user2.setFamilyName("Staykov");
        user2.setPhone("089111111");
        user2.setId(3l);
        userRepository.save(user2);
    }
}
