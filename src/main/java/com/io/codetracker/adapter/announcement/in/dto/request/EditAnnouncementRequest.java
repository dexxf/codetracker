package com.io.codetracker.adapter.announcement.in.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public record EditAnnouncementRequest(
        String message,
        List<MultipartFile> newAttachments,
        List<UUID> attachmentIdsToRemove
) { }