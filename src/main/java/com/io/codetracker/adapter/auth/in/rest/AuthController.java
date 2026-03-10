package com.io.codetracker.adapter.auth.in.rest;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.io.codetracker.adapter.auth.out.security.AuthPrincipal;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
        
@GetMapping("/check")
public ResponseEntity<Map<String, Object>> checkAuthentication(@AuthenticationPrincipal AuthPrincipal principal) {
    if (principal == null) {
        return ResponseEntity.ok(Map.of("authenticated", false));
    }

    return ResponseEntity.ok(Map.of(
            "authenticated", true,
            "authId", principal.getUsername(),
            "roles", principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(),
            "email", principal.getEmail(),
            "username", principal.getAuthUsername()
    ));
}

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", "");
        cookie.setHttpOnly(false);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

}