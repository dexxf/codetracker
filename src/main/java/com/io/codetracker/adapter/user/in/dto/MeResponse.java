package com.io.codetracker.adapter.user.in.dto;

import java.util.List;

public record MeResponse(String userId, String username, List<String> roles) {

}
