package com.io.codetracker.infrastructure.activity.persistence.repository;

import com.io.codetracker.infrastructure.activity.persistence.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaActivityRepository extends JpaRepository<ActivityEntity, String> {
    boolean existsByClassroomIdAndActivityId(String classroomId, String activityId);
}
