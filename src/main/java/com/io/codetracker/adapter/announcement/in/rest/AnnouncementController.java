package com.io.codetracker.adapter.announcement.in.rest;

import com.io.codetracker.adapter.announcement.in.dto.request.CreateAnnouncementRequest;
import com.io.codetracker.adapter.announcement.in.dto.response.CreateAnnouncementResponse;
import com.io.codetracker.adapter.auth.out.security.AuthPrincipal;
import com.io.codetracker.application.announcement.command.CreateAnnouncementCommand;
import com.io.codetracker.application.announcement.error.CreateAnnouncementError;
import com.io.codetracker.application.announcement.port.in.CreateAnnouncementUseCase;
import com.io.codetracker.application.announcement.result.CreateAnnouncementResult;
import com.io.codetracker.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/classroom/{classroomId}/announcement")
public class AnnouncementController {

    private final CreateAnnouncementUseCase createAnnouncementUseCase;

    @PostMapping
    public ResponseEntity<CreateAnnouncementResponse> createAnnouncement(
            @PathVariable UUID classroomId,
            @RequestPart("data") CreateAnnouncementRequest request,
            @RequestPart(value = "attachments", required = false) List<MultipartFile> attachments,
            @AuthenticationPrincipal AuthPrincipal authPrincipal
    ) {
        var command = new CreateAnnouncementCommand(
                classroomId,
                authPrincipal.getUserId(),
                request.message(),
                toAttachmentUploads(attachments),
                Instant.now()
        );

        Result<CreateAnnouncementResult, CreateAnnouncementError> result = createAnnouncementUseCase.createAnnouncement(command);

        if (result.success()) {
            var data = result.data();
            return ResponseEntity.ok(new CreateAnnouncementResponse(
                    data.announcementId(),
                    data.message(),
                    data.attachments(),
                    data.createdAt()
            ));
        }

        return ResponseEntity.badRequest().build();
    }

    private List<CreateAnnouncementCommand.AttachmentUpload> toAttachmentUploads(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            return List.of();
        }

        return files.stream()
                .map(file -> {
                    try {
                        return new CreateAnnouncementCommand.AttachmentUpload(
                                file.getBytes(),
                                file.getOriginalFilename()
                        );
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to read attachment: " + file.getOriginalFilename(), e);
                    }
                })
                .toList();
    }
}