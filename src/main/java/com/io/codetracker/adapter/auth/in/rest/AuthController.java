package com.io.codetracker.adapter.auth.in.rest;

import lombok.AllArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.io.codetracker.adapter.auth.out.security.AuthPrincipal;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
        
@GetMapping("/check")
public ResponseEntity<?> checkAuthentication(@AuthenticationPrincipal AuthPrincipal principal) {

    if (principal == null) {
        return ResponseEntity.ok(Map.of("authenticated", false));
    }

    return ResponseEntity.ok(Map.of(
        "authenticated", true,
        "authId", principal.getUsername(),
        "roles", principal.getAuthorities().stream().map(e -> e.getAuthority()).toList()
    ));
}

}