package com.io.kira.application.announcement.service;

import com.io.kira.application.announcement.command.CreateAnnouncementCommand;
import com.io.kira.application.announcement.error.CreateAnnouncementError;
import com.io.kira.application.announcement.port.in.CreateAnnouncementUseCase;
import com.io.kira.application.announcement.port.out.AnnouncementAppRepository;
import com.io.kira.application.announcement.port.out.AnnouncementAttachmentStoragePort;
import com.io.kira.application.announcement.port.out.AttachmentTypeResolverPort;
import com.io.kira.application.announcement.port.out.ClassroomAnnouncementAppRepository;
import com.io.kira.application.announcement.result.CreateAnnouncementResult;
import com.io.kira.common.result.Result;
import com.io.kira.domain.announcement.entity.Announcement;
import com.io.kira.domain.announcement.entity.AnnouncementAttachment;
import com.io.kira.domain.announcement.exception.UnsupportedAttachmentTypeException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                    AnnouncementAttachmentRollback.deleteUploaded(
                            attachmentStorage, command.classroomId(), uploadedAttachments);
                    return Result.fail(CreateAnnouncementError.CANT_UPLOAD_FILE);
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
            announcementRepository.save(announcement);
        } catch (UnsupportedAttachmentTypeException ex) {
            AnnouncementAttachmentRollback.deleteUploaded(
                    attachmentStorage, command.classroomId(), uploadedAttachments);
            return Result.fail(CreateAnnouncementError.UNSUPPORTED_FILE_TYPE);
        } catch (IOException ex) {
            AnnouncementAttachmentRollback.deleteUploaded(
                    attachmentStorage, command.classroomId(), uploadedAttachments);
            return Result.fail(CreateAnnouncementError.CANT_UPLOAD_FILE);
        } catch (RuntimeException ex) {
            AnnouncementAttachmentRollback.deleteUploaded(
                    attachmentStorage, command.classroomId(), uploadedAttachments);
            throw ex;
        }

        return Result.ok(CreateAnnouncementResult.toResult(announcement));
    }
}
