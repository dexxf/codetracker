package com.io.codetracker.adapter.announcement.in.rest;

import com.io.codetracker.adapter.announcement.in.dto.request.CreateAnnouncementRequest;
import com.io.codetracker.adapter.announcement.in.dto.request.EditAnnouncementRequest;
import com.io.codetracker.adapter.announcement.in.dto.response.CreateAnnouncementResponse;
import com.io.codetracker.adapter.announcement.in.mapper.AnnouncementHttpMapper;
import com.io.codetracker.adapter.auth.out.security.AuthPrincipal;
import com.io.codetracker.application.announcement.command.CreateAnnouncementCommand;
import com.io.codetracker.application.announcement.command.EditAnnouncementCommand;
import com.io.codetracker.application.announcement.error.CreateAnnouncementError;
import com.io.codetracker.application.announcement.error.EditAnnouncementError;
import com.io.codetracker.application.announcement.port.in.CreateAnnouncementUseCase;
import com.io.codetracker.application.announcement.port.in.EditAnnouncementUseCase;
import com.io.codetracker.application.announcement.result.CreateAnnouncementResult;
import com.io.codetracker.application.announcement.result.EditAnnouncementResult;
import com.io.codetracker.common.result.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/classroom/{classroomId}/announcement")
public class AnnouncementController {

    private final CreateAnnouncementUseCase createAnnouncementUseCase;
    private final EditAnnouncementUseCase editAnnouncementUseCase;

    @PostMapping
    public ResponseEntity<?> createAnnouncement(
            @PathVariable UUID classroomId,
            @Valid @RequestPart("data") CreateAnnouncementRequest request,
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
            return ResponseEntity.status(HttpStatus.CREATED).body(new CreateAnnouncementResponse(
                    data.announcementId(),
                    data.message(),
                    data.attachments(),
                    data.createdAt()
            ));
        }

        return ResponseEntity
                .status(AnnouncementHttpMapper.toStatus(result.error()))
                .body(Map.of("error", AnnouncementHttpMapper.toMessage(result.error())));
    }

    @PatchMapping(value = "/{announcementId}", consumes = "multipart/form-data")
    public ResponseEntity<?> editAnnouncement(
            @PathVariable UUID classroomId,
            @PathVariable UUID announcementId,
            @AuthenticationPrincipal AuthPrincipal authPrincipal,
            @ModelAttribute EditAnnouncementRequest request
    ) {
        List<EditAnnouncementCommand.AttachmentUpload> attachmentUploads = toEditAttachmentUploads(request.newAttachments());

        EditAnnouncementCommand command = new EditAnnouncementCommand(
                announcementId,
                classroomId,
                authPrincipal.getUserId(),
                request.message(),
                attachmentUploads,
                request.attachmentIdsToRemove(),
                Instant.now()
        );

        Result<EditAnnouncementResult, EditAnnouncementError> result = editAnnouncementUseCase.editAnnouncement(command);

        return result.success()
                ? ResponseEntity.ok(result.data())
                : ResponseEntity.status(AnnouncementHttpMapper.toStatus(result.error()))
                    .body(Map.of("error", AnnouncementHttpMapper.toMessage(result.error())));
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

    private List<EditAnnouncementCommand.AttachmentUpload> toEditAttachmentUploads(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            return List.of();
        }

        return files.stream()
                .map(file -> {
                    try {
                        return new EditAnnouncementCommand.AttachmentUpload(
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
