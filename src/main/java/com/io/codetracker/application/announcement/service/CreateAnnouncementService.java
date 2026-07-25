package com.io.codetracker.application.announcement.service;

import com.io.codetracker.application.announcement.command.CreateAnnouncementCommand;
import com.io.codetracker.application.announcement.error.CreateAnnouncementError;
import com.io.codetracker.application.announcement.port.in.CreateAnnouncementUseCase;
import com.io.codetracker.application.announcement.port.out.AnnouncementAppRepository;
import com.io.codetracker.application.announcement.port.out.AnnouncementAttachmentStoragePort;
import com.io.codetracker.application.announcement.result.CreateAnnouncementResult;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.announcement.entity.Announcement;
import com.io.codetracker.domain.announcement.entity.AnnouncementAttachment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CreateAnnouncementService implements CreateAnnouncementUseCase {

    private final AnnouncementAppRepository announcementRepository;
    private final AnnouncementAttachmentStoragePort attachmentStorage;

    @Override
    @Transactional
    public Result<CreateAnnouncementResult, CreateAnnouncementError> createAnnouncement(CreateAnnouncementCommand command) {

        List<AnnouncementAttachment> announcementAttachments = new ArrayList<>();

        for (CreateAnnouncementCommand.AttachmentUpload attachmentUpload : command.attachments()) {
            UUID attachmentId = UUID.randomUUID();

            try {
                String url = attachmentStorage.upload(attachmentUpload.content(), command.classroomId(), attachmentId);
                announcementAttachments.add(new AnnouncementAttachment(attachmentId, url, attachmentUpload.type()));
            } catch (IOException ex) {
                rollbackUploadedAttachments(command.classroomId(), announcementAttachments);
                return Result.fail(CreateAnnouncementError.CANT_UPLOAD_FILE);
            }
        }

        Announcement announcement = Announcement.create(
                command.classroomId(),
                command.authorId(),
                command.message(),
                announcementAttachments,
                command.now()
        );

        announcementRepository.save(announcement);

        return Result.ok(CreateAnnouncementResult.toResult(announcement));
    }

    private void rollbackUploadedAttachments(UUID classroomId, List<AnnouncementAttachment> uploaded) {
        for (AnnouncementAttachment attachment : uploaded) {
            try {
                attachmentStorage.delete(classroomId, attachment.attachmentId());
            } catch (IOException ex) {
                log.warn("Failed to rollback attachment {} in classroom {}", attachment.attachmentId(), classroomId, ex);
            }
        }
    }
}