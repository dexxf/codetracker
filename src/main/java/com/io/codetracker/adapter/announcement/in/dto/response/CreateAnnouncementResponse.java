package com.io.codetracker.adapter.announcement.in.dto.response;

import com.io.codetracker.application.announcement.result.AttachmentData;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record CreateAnnouncementResponse(
        UUID announcementId,
        String message,
        List<AttachmentData> attachments,
        Instant createdAt
) {}