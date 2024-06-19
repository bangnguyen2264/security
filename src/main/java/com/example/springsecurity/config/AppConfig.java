package com.example.springsecurity.config;

import com.example.springsecurity.model.entity.Role;
import com.example.springsecurity.model.entity.User;
import com.example.springsecurity.repository.RoleRepository;
import com.example.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class AppConfig {

    @Bean
    public CommandLineRunner commandLineRunner(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            Role user = Role.builder()
                    .name("ROLE_USER")
                    .build();
            Role admin = Role.builder()
                    .name("ROLE_ADMIN")
                    .build();

            roleRepository.save(user);
            roleRepository.save(admin);

            User demoUser = User.builder()
                    .lastname("Admin")
                    .firstname("System")
                    .username("admin")
                    .password(passwordEncoder.encode("password"))
                    .role(admin)
                    .build();

            userRepository.save(demoUser);

            // List of first names and last names
            String[] firstNames = {"John", "Jane", "Michael", "John", "Chris", "Anna", "David", "Sophia", "Daniel", "Olivia"};
            String[] lastNames = {"Jones", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Martinez", "Hernandez"};

            // Create 10 additional users with different names
            for (int i = 0; i < 10; i++) {
                User userInstance = User.builder()
                        .lastname(lastNames[i])
                        .firstname(firstNames[i])
                        .username("user" + (i + 1))
                        .password(passwordEncoder.encode("password" + (i + 1)))
                        .role(user)
                        .build();
                userRepository.save(userInstance);
            }
        };
    }

}
