package com.io.codetracker.application.user.response;

import java.util.Map;

public record UserRegistrationResponseDTO(boolean success, Map<String, Object> data, String message) {

    public static UserRegistrationResponseDTO ok(Map<String, Object> data, String message){
        return new UserRegistrationResponseDTO(true, data, message);
    }

    public static UserRegistrationResponseDTO fail(String message){
        return new UserRegistrationResponseDTO(false, null, message);
    }
}
