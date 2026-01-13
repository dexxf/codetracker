package com.io.codetracker.adapter.auth.in.rest;

import com.io.codetracker.application.auth.command.AuthRegistrationCommand;
import com.io.codetracker.application.auth.dto.AuthRegistrationDTO;
import com.io.codetracker.application.auth.service.AuthRegistration;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthRegistration authRegistration;

    @PostMapping("/api/auth/register")
    public ResponseEntity<AuthRegistrationDTO> registerAuth(@Valid @RequestBody AuthRegistrationCommand command) {
        AuthRegistrationDTO authResult = authRegistration.execute(command);
        return !authResult.success() ? ResponseEntity.badRequest().body(authResult) : ResponseEntity.status(HttpStatus.OK).body(authResult);
    }

}