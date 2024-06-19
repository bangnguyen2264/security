package com.example.springsecurity.service.impl;

import com.example.springsecurity.exception.InvalidRefreshTokenException;
import com.example.springsecurity.model.dto.AuthDto;
import com.example.springsecurity.model.entity.Role;
import com.example.springsecurity.model.entity.User;
import com.example.springsecurity.model.form.SignInForm;
import com.example.springsecurity.model.form.SignUpForm;
import com.example.springsecurity.repository.RoleRepository;
import com.example.springsecurity.repository.UserRepository;

import com.example.springsecurity.security.JwtService;
import com.example.springsecurity.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2

public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;



    @Override
    public AuthDto login(SignInForm form) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(form.getUserName(), form.getPassword())
        );

        String accessToken = jwtService.generateAccessToken(authentication);
        String refreshToken = jwtService.generateRefreshToken(authentication);

        User user = userRepository.findByUsername(form.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + form.getUserName()));

        log.info("User {} logged in", user.getUsername());
        return AuthDto.from(user, accessToken, refreshToken);    }

    @Override
    public String register(SignUpForm form) {
        if (userRepository.existsByUsername(form.getUserName())) {
            throw new IllegalArgumentException("Username already exists");
        }

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalArgumentException("Not found role ROLE_USER"));

        User user = User.builder()
                .firstname(form.getFirstname())
                .lastname(form.getLastname())
                .username(form.getUserName())
                .password(passwordEncoder.encode(form.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);

        log.info("User {} registered", user.getUsername());
        return "Success register new user";
    }

    @Override
    public AuthDto refreshJWT(String refreshToken) {
        if (refreshToken != null) {
            refreshToken = refreshToken.replaceFirst("Bearer ", "");
            if (jwtService.validateRefreshToken(refreshToken)) {
                Authentication auth = jwtService.createAuthentication(refreshToken);

                User user = userRepository.findByUsername(auth.getName())
                        .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + auth.getName()));

                log.info("User {} refreshed token", user.getUsername());
                return AuthDto.from(user, jwtService.generateAccessToken(auth), refreshToken);
            }
        }
        throw new InvalidRefreshTokenException(refreshToken);
    }
}
