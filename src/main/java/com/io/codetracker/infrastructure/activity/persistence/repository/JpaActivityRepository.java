package com.io.codetracker.infrastructure.activity.persistence.repository;

import com.io.codetracker.domain.activity.valueObject.ActivityStatus;
import com.io.codetracker.infrastructure.activity.persistence.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaActivityRepository extends JpaRepository<ActivityEntity, String> {
    boolean existsByClassroomIdAndActivityId(String classroomId, String activityId);
    List<ActivityEntity> findByClassroomIdAndCreatedByProfessorId(String classroomId, String createdByProfessorId);
    long countByClassroomIdAndStatus(String classroomId, ActivityStatus status);
    long countByClassroomId(String classroomId);
}