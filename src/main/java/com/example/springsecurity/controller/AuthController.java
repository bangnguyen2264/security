package com.example.springsecurity.controller;

import com.example.springsecurity.model.form.SignInForm;
import com.example.springsecurity.model.form.SignUpForm;
import com.example.springsecurity.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody SignInForm form){
        return ResponseEntity.ok(authService.login(form));
    }

    @PostMapping("/register")
    public ResponseEntity<String> login(@RequestBody  SignUpForm form){
        return ResponseEntity.ok(authService.register(form));
    }

    @GetMapping("/refresh")
    public ResponseEntity refreshToken(@RequestHeader("X-Refresh-Token") String refreshToken){
        return ResponseEntity.ok(authService.refreshJWT(refreshToken));
    }
}
