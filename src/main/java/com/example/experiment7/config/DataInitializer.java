package com.example.experiment7.config;

import com.example.experiment7.entity.Role;
import com.example.experiment7.entity.RoleName;
import com.example.experiment7.entity.User;
import com.example.experiment7.repository.RoleRepository;
import com.example.experiment7.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Role roleUser = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Role(RoleName.ROLE_USER)));

        Role roleAdmin = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseGet(() -> roleRepository.save(new Role(RoleName.ROLE_ADMIN)));

        if (!userRepository.existsByUsername("user1")) {
            User user = new User();
            user.setUsername("user1");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRoles(Set.of(roleUser));
            userRepository.save(user);
        }

        if (!userRepository.existsByUsername("admin1")) {
            User admin = new User();
            admin.setUsername("admin1");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(Set.of(roleAdmin));
            userRepository.save(admin);
        }
    }
}
