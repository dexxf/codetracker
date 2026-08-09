package com.io.kira.domain.auth.service;

public interface PasswordHasher {
     String encode(CharSequence rawPassword);
     boolean matches(CharSequence rawPassword, String encodedPassword);
}
