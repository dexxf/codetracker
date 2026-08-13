package com.io.kira.adapter.activity.out.persistence.repository;


import java.util.UUID;
import com.io.kira.adapter.activity.out.cache.ActivityCacheNames;
import com.io.kira.application.activity.port.out.ActivityClassroomAppPort;
import com.io.kira.domain.classroom.valueObject.StudentStatus;
import com.io.kira.infrastructure.activity.persistence.repository.JpaActivityRepository;
import com.io.kira.infrastructure.classroom.persistence.repository.JpaClassroomRepository;
import com.io.kira.infrastructure.classroom.persistence.repository.JpaClassroomStudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.Cacheable;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ActivityClassroomAppAdapter implements ActivityClassroomAppPort {

    private final JpaClassroomRepository jpa;
    private final JpaClassroomStudentRepository jpaClassroomStudentRepository;
    private final JpaActivityRepository jpaActivityRepository;

    @Override
    public boolean existsByClassroomId(UUID s) {
        return jpa.existsByClassroomId(s);
    }

    @Override
    public boolean existsByClassroomIdAndInstructorUserId(UUID classroomId, UUID userId) {
        return jpa.existsByClassroomIdAndInstructorUserId(classroomId, userId);
    }

    @Override
    public boolean existsByClassroomIdAndStudentUserId(UUID classroomId, UUID userId) {
        return jpaClassroomStudentRepository.existsByClassroom_ClassroomIdAndStudentUserIdAndStatus(
                classroomId,
                userId,
                StudentStatus.ACTIVE
        );
    }

    @Override
    @Cacheable(value = ActivityCacheNames.ACTIVITY,
            key = "@activityCacheKey.existsByClassroomIdAndActivityId(#classroomId, #activityId)")
    public boolean existsByClassroomIdAndActivityId(UUID classroomId, String activityId) {
        return jpaActivityRepository.existsByClassroomEntity_ClassroomIdAndActivityId(classroomId, activityId);
    }

    @Override
    @Cacheable(value = ActivityCacheNames.ACTIVITY,
            key = "@activityCacheKey.maxScoreByClassroomIdAndActivityId(#classroomId, #activityId)",
            unless = "#result == null")
    public Optional<Integer> findMaxScoreByClassroomIdAndActivityId(UUID classroomId, String activityId) {
        return jpaActivityRepository.findMaxScoreByClassroomIdAndActivityId(classroomId, activityId);
    }

}

