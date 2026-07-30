package com.io.codetracker.application.announcement.service;

import com.io.codetracker.application.announcement.command.DeleteAnnouncementCommand;
import com.io.codetracker.application.announcement.error.DeleteAnnouncementError;
import com.io.codetracker.application.announcement.port.in.DeleteAnnouncementUseCase;
import com.io.codetracker.application.announcement.port.out.AnnouncementAppRepository;
import com.io.codetracker.application.announcement.port.out.AnnouncementAttachmentStoragePort;
import com.io.codetracker.application.announcement.port.out.ClassroomAnnouncementAppRepository;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.announcement.entity.Announcement;
import com.io.codetracker.domain.announcement.entity.AnnouncementAttachment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Service
@AllArgsConstructor
public class DeleteAnnouncementService implements DeleteAnnouncementUseCase {

    private final AnnouncementAppRepository announcementRepository;
    private final ClassroomAnnouncementAppRepository classroomAnnouncementAppRepository;
    private final AnnouncementAttachmentStoragePort attachmentStorage;

    @Override
    @Transactional
    public Result<Void, DeleteAnnouncementError> deleteAnnouncement(DeleteAnnouncementCommand command) {
        if (!classroomAnnouncementAppRepository.existsByClassroomId(command.classroomId())) {
            return Result.fail(DeleteAnnouncementError.CLASSROOM_NOT_FOUND);
        }

        if (!classroomAnnouncementAppRepository.isClassroomInstructor(
                command.classroomId(),
                command.instructorId())) {
            return Result.fail(DeleteAnnouncementError.NOT_CLASSROOM_INSTRUCTOR);
        }

        Announcement announcement = announcementRepository.findById(command.announcementId()).orElse(null);
        if (announcement == null || !announcement.classroomId().equals(command.classroomId())) {
            return Result.fail(DeleteAnnouncementError.ANNOUNCEMENT_NOT_FOUND);
        }

        announcementRepository.deleteById(announcement.announcementId());

        for (AnnouncementAttachment attachment : announcement.attachments()) {
            try {
                attachmentStorage.delete(
                        command.classroomId(),
                        attachment.attachmentId(),
                        attachment.resourceType()
                );
            } catch (IOException ex) {
                log.warn("Failed to delete attachment {} after deleting announcement {}",
                        attachment.attachmentId(), announcement.announcementId(), ex);
            }
        }

        return Result.ok(null);
    }
}
