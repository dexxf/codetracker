package com.io.codetracker.application.announcement.service;

import com.io.codetracker.application.announcement.command.EditAnnouncementCommand;
import com.io.codetracker.application.announcement.error.EditAnnouncementError;
import com.io.codetracker.application.announcement.port.in.EditAnnouncementUseCase;
import com.io.codetracker.application.announcement.port.out.AnnouncementAppRepository;
import com.io.codetracker.application.announcement.port.out.AnnouncementAttachmentStoragePort;
import com.io.codetracker.application.announcement.port.out.AttachmentTypeResolverPort;
import com.io.codetracker.application.announcement.port.out.ClassroomAnnouncementAppRepository;
import com.io.codetracker.application.announcement.result.EditAnnouncementResult;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.announcement.entity.Announcement;
import com.io.codetracker.domain.announcement.entity.AnnouncementAttachment;
import com.io.codetracker.domain.announcement.exception.UnsupportedAttachmentTypeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class EditAnnouncementService implements EditAnnouncementUseCase {

    private final AnnouncementAppRepository announcementRepository;
    private final ClassroomAnnouncementAppRepository classroomAnnouncementAppRepository;
    private final AnnouncementAttachmentStoragePort attachmentStorage;
    private final AttachmentTypeResolverPort typeResolver;

    @Override
    @Transactional
    public Result<EditAnnouncementResult, EditAnnouncementError> editAnnouncement(EditAnnouncementCommand command) {

        if (!classroomAnnouncementAppRepository.existsByClassroomId(command.classroomId())) {
            return Result.fail(EditAnnouncementError.CLASSROOM_NOT_FOUND);
        }

        if (!classroomAnnouncementAppRepository.isClassroomInstructor(
                command.classroomId(),
                command.editorId())) {
            return Result.fail(EditAnnouncementError.NOT_CLASSROOM_INSTRUCTOR);
        }

        Optional<Announcement> maybeAnnouncement = announcementRepository.findById(command.announcementId());
        if (maybeAnnouncement.isEmpty()) {
            return Result.fail(EditAnnouncementError.ANNOUNCEMENT_NOT_FOUND);
        }

        Announcement announcement = maybeAnnouncement.get();

        if (!announcement.classroomId().equals(command.classroomId())) {
            return Result.fail(EditAnnouncementError.ANNOUNCEMENT_NOT_FOUND);
        }

        List<AnnouncementAttachment> uploadedAttachments = new ArrayList<>();

        try {
            List<UUID> toRemove = command.attachmentIdsToRemove() == null
                    ? List.of() : command.attachmentIdsToRemove();

            List<AnnouncementAttachment> attachmentsToRemove = new ArrayList<>();

            for (UUID attachmentId : toRemove) {
                AnnouncementAttachment attachment = announcement.findAttachment(attachmentId).orElse(null);
                if (attachment == null) {
                    AnnouncementAttachmentRollback.deleteUploaded(
                            attachmentStorage, command.classroomId(), uploadedAttachments);
                    return Result.fail(EditAnnouncementError.ATTACHMENT_NOT_FOUND);
                }
                attachmentsToRemove.add(attachment);
            }

            List<EditAnnouncementCommand.AttachmentUpload> newUploads =
                    command.newAttachments() == null ? List.of() : command.newAttachments();

            for (EditAnnouncementCommand.AttachmentUpload upload : newUploads) {
                if (upload == null || upload.content() == null) {
                    AnnouncementAttachmentRollback.deleteUploaded(
                            attachmentStorage, command.classroomId(), uploadedAttachments);
                    return Result.fail(EditAnnouncementError.CANT_UPLOAD_FILE);
                }

                AnnouncementAttachment attachment = AnnouncementAttachmentUploader.upload(
                        upload.content(),
                        upload.filename(),
                        command.classroomId(),
                        attachmentStorage,
                        typeResolver
                );
                uploadedAttachments.add(attachment);    
                announcement.addAttachment(attachment);
            }

            if (command.message() != null) {
                announcement.updateMessage(command.message(), command.now());
            }

            attachmentsToRemove.forEach(attachment -> announcement.removeAttachment(attachment.attachmentId()));

            announcementRepository.save(announcement);

            for (AnnouncementAttachment attachment : attachmentsToRemove) {
                try {
                    attachmentStorage.delete(
                            command.classroomId(), attachment.attachmentId(), attachment.resourceType());
                } catch (IOException ex) {
                    log.warn("Failed to delete removed attachment {} in classroom {}",
                            attachment.attachmentId(), command.classroomId(), ex);
                }
            }

        } catch (UnsupportedAttachmentTypeException ex) {
            AnnouncementAttachmentRollback.deleteUploaded(
                    attachmentStorage, command.classroomId(), uploadedAttachments);
            return Result.fail(EditAnnouncementError.UNSUPPORTED_FILE_TYPE);
        } catch (IOException ex) {
            AnnouncementAttachmentRollback.deleteUploaded(
                    attachmentStorage, command.classroomId(), uploadedAttachments);
            return Result.fail(EditAnnouncementError.CANT_UPLOAD_FILE);
        } catch (RuntimeException ex) {
            AnnouncementAttachmentRollback.deleteUploaded(
                    attachmentStorage, command.classroomId(), uploadedAttachments);
            throw ex;
        }

        return Result.ok(EditAnnouncementResult.toResult(announcement));
    }
}
