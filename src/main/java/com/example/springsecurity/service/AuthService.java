package com.example.springsecurity.service;

import com.example.springsecurity.model.dto.AuthDto;
import com.example.springsecurity.model.form.SignInForm;
import com.example.springsecurity.model.form.SignUpForm;

public interface AuthService {
    AuthDto login(SignInForm form);
    String register(SignUpForm form);
    AuthDto refreshJWT(String refreshToken);
}
