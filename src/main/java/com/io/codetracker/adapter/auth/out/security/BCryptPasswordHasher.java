package com.io.codetracker.adapter.auth.out.security;

import com.io.codetracker.domain.auth.service.PasswordHasher;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@Primary
public class BCryptPasswordHasher implements PasswordEncoder, PasswordHasher {

    private final BCryptPasswordEncoder encoder;

    public BCryptPasswordHasher (BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String encode( CharSequence rawPassword) {
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword,  String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
