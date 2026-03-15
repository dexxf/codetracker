package com.io.codetracker.adapter.user.in.dto.response;

import java.util.Map;

public record UserRegistrationResponseDTO(Map<String, Object> data, String message) {

    public static UserRegistrationResponseDTO ok(Map<String, Object> data, String message){
        return new UserRegistrationResponseDTO(data, message);
    }

    public static UserRegistrationResponseDTO fail(String message){
        return new UserRegistrationResponseDTO(null, message);
    }
}
