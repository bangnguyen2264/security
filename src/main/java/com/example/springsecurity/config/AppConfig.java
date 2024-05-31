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
            User demoUser = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("password"))
                    .role(admin)
                    .build();
            roleRepository.save(user);
            roleRepository.save(admin);
            userRepository.save(demoUser);
        };
    }

}
