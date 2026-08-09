package com.io.kira.adapter.announcement.in.dto.request;

import jakarta.validation.constraints.Size;

public record CreateAnnouncementRequest(
        @Size(max = 5000, message = "Message must not exceed 5000 characters")
        String message
) {}