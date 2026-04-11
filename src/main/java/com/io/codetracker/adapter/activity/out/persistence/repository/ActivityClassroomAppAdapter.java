package com.io.codetracker.adapter.activity.out.persistence.repository;

import com.io.codetracker.application.activity.port.out.ActivityClassroomAppPort;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;
import com.io.codetracker.infrastructure.activity.persistence.repository.JpaActivityRepository;
import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomRepository;
import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomStudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ActivityClassroomAppAdapter implements ActivityClassroomAppPort {

    private final JpaClassroomRepository jpa;
    private final JpaClassroomStudentRepository jpaClassroomStudentRepository;
    private final JpaActivityRepository jpaActivityRepository;

    @Override
    public boolean existsByClassroomId(String s) {
        return jpa.existsByClassroomId(s);
    }

    @Override
    public boolean existsByClassroomIdAndInstructorUserId(String classroomId, String userId) {
        return jpa.existsByClassroomIdAndInstructorUserId(classroomId, userId);
    }

    @Override
    public String findClassroomOwnerByClassroomId(String classroomId) {
        return jpa.findInstructorUserIdByClassroomId(classroomId);
    }


    @Override
    public boolean existsByClassroomIdAndStudentUserId(String classroomId, String userId) {
        return jpaClassroomStudentRepository.existsByClassroom_ClassroomIdAndStudentUserIdAndStatus(
                classroomId,
                userId,
                StudentStatus.ACTIVE
        );
    }

    @Override
    public boolean existsByClassroomIdAndActivityId(String classroomId, String activityId) {
        return jpaActivityRepository.existsByClassroomEntity_ClassroomIdAndActivityId(classroomId, activityId);
    }

    @Override
    public Optional<Integer> findMaxScoreByClassroomIdAndActivityId(String classroomId, String activityId) {
        return jpaActivityRepository.findMaxScoreByClassroomIdAndActivityId(classroomId, activityId);
    }

}
