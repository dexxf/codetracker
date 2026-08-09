package com.io.kira.application.announcement.service;

import com.io.kira.application.announcement.command.ViewAnnouncementsCommand;
import com.io.kira.application.announcement.error.ViewAnnouncementsError;
import com.io.kira.application.announcement.port.in.ViewAnnouncementsUseCase;
import com.io.kira.application.announcement.port.out.AnnouncementAppRepository;
import com.io.kira.application.announcement.port.out.ClassroomAnnouncementAppRepository;
import com.io.kira.application.announcement.result.AnnouncementViewData;
import com.io.kira.common.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ViewAnnouncementsService implements ViewAnnouncementsUseCase {

    private final AnnouncementAppRepository announcementRepository;
    private final ClassroomAnnouncementAppRepository classroomAnnouncementAppRepository;

    @Override
    public Result<List<AnnouncementViewData>, ViewAnnouncementsError> viewAnnouncements(
            ViewAnnouncementsCommand command
    ) {
        if (!classroomAnnouncementAppRepository.existsByClassroomId(command.classroomId())) {
            return Result.fail(ViewAnnouncementsError.CLASSROOM_NOT_FOUND);
        }

        boolean isInstructor = classroomAnnouncementAppRepository.isClassroomInstructor(
                command.classroomId(), command.viewerId());
        boolean isStudent = classroomAnnouncementAppRepository.isActiveClassroomStudent(
                command.classroomId(), command.viewerId());

        if (!isInstructor && !isStudent) {
            return Result.fail(ViewAnnouncementsError.USER_NOT_CLASSROOM_MEMBER);
        }

        List<AnnouncementViewData> announcements = announcementRepository
                .findAllByClassroomId(command.classroomId())
                .stream()
                .map(AnnouncementViewData::from)
                .toList();

        return Result.ok(announcements);
    }
}
