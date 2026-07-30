package com.io.codetracker.adapter.announcement.out.service;

import com.io.codetracker.application.announcement.port.out.ClassroomAnnouncementAppRepository;
import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomRepository;
import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomStudentRepository;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@AllArgsConstructor
public class ClassroomAnnouncementAppRepositoryImpl implements ClassroomAnnouncementAppRepository {

    private JpaClassroomRepository repository;
    private JpaClassroomStudentRepository classroomStudentRepository;

    @Override
    public boolean existsByClassroomId(UUID classroomId) {
        return repository.existsByClassroomId(classroomId);
    }

    @Override
    public boolean isClassroomInstructor(UUID classroomId, UUID userId) {
        return repository.existsByClassroomIdAndInstructorUserId(classroomId,userId);
    }

    @Override
    public boolean isActiveClassroomStudent(UUID classroomId, UUID userId) {
        return classroomStudentRepository.existsByClassroom_ClassroomIdAndStudentUserIdAndStatus(
                classroomId, userId, StudentStatus.ACTIVE);
    }
}
