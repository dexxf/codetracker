package com.io.codetracker.application.announcement.service;

import com.io.codetracker.application.announcement.command.CreateAnnouncementCommand;
import com.io.codetracker.application.announcement.error.CreateAnnouncementError;
import com.io.codetracker.application.announcement.port.in.CreateAnnouncementUseCase;
import com.io.codetracker.application.announcement.port.out.AnnouncementAppRepository;
import com.io.codetracker.application.announcement.port.out.AnnouncementAttachmentStoragePort;
import com.io.codetracker.application.announcement.port.out.AttachmentTypeResolverPort;
import com.io.codetracker.application.announcement.port.out.ClassroomAnnouncementAppRepository;
import com.io.codetracker.application.announcement.result.CreateAnnouncementResult;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.announcement.entity.Announcement;
import com.io.codetracker.domain.announcement.entity.AnnouncementAttachment;
import com.io.codetracker.domain.announcement.exception.UnsupportedAttachmentTypeException;
import com.io.codetracker.domain.announcement.valueobject.AttachmentType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CreateAnnouncementService implements CreateAnnouncementUseCase {

    private final AnnouncementAppRepository announcementRepository;
    private final ClassroomAnnouncementAppRepository classroomAnnouncementAppRepository;
    private final AnnouncementAttachmentStoragePort attachmentStorage;
    private final AttachmentTypeResolverPort typeResolver;

    @Override
    @Transactional
    public Result<CreateAnnouncementResult, CreateAnnouncementError> createAnnouncement(CreateAnnouncementCommand command) {

        if (!classroomAnnouncementAppRepository.existsByClassroomId(command.classroomId())) {
            return Result.fail(CreateAnnouncementError.CLASSROOM_NOT_FOUND);
        }

        if (!classroomAnnouncementAppRepository.isClassroomInstructor(
                command.classroomId(),
                command.authorId())) {
            return Result.fail(CreateAnnouncementError.NOT_CLASSROOM_INSTRUCTOR);
        }

        List<AnnouncementAttachment> uploadedAttachments = new ArrayList<>();

        Announcement announcement = Announcement.create(
                command.classroomId(),
                command.authorId(),
                command.message(),
                List.of(),
                command.now()
        );

        List<CreateAnnouncementCommand.AttachmentUpload> uploads =
                command.attachments() == null ? List.of() : command.attachments();

        try {
            for (CreateAnnouncementCommand.AttachmentUpload upload : uploads) {
                if (upload == null || upload.content() == null) {
                    rollbackUploadedAttachments(command.classroomId(), uploadedAttachments);
                    return Result.fail(CreateAnnouncementError.CANT_UPLOAD_FILE);
                }

            UUID attachmentId = UUID.randomUUID();

                AttachmentType type = typeResolver.resolve(new ByteArrayInputStream(upload.content()), upload.filename());

                var uploadedAttachment = attachmentStorage.upload(upload.content(), command.classroomId(), attachmentId);
                AnnouncementAttachment attachment = new AnnouncementAttachment(
                        attachmentId,
                        uploadedAttachment.url(),
                        type,
                        uploadedAttachment.resourceType()
                );
                uploadedAttachments.add(attachment);
                announcement.addAttachment(attachment);
            }
            announcementRepository.save(announcement);
        } catch (UnsupportedAttachmentTypeException ex) {
            rollbackUploadedAttachments(command.classroomId(), uploadedAttachments);
            return Result.fail(CreateAnnouncementError.UNSUPPORTED_FILE_TYPE);
        } catch (IOException ex) {
            rollbackUploadedAttachments(command.classroomId(), uploadedAttachments);
            return Result.fail(CreateAnnouncementError.CANT_UPLOAD_FILE);
        } catch (RuntimeException ex) {
            rollbackUploadedAttachments(command.classroomId(), uploadedAttachments);
            throw ex;
        }

        return Result.ok(CreateAnnouncementResult.toResult(announcement));
    }

    private void rollbackUploadedAttachments(UUID classroomId, List<AnnouncementAttachment> uploadedAttachments) {
        for (AnnouncementAttachment attachment : uploadedAttachments) {
            try {
                attachmentStorage.delete(classroomId, attachment.attachmentId(), attachment.resourceType());
            } catch (IOException ex) {
                log.warn("Failed to rollback attachment {} in classroom {}", attachment.attachmentId(), classroomId, ex);
            }
        }
    }
}
