package com.io.codetracker.infrastructure.activity.persistence.repository;

import com.io.codetracker.infrastructure.activity.persistence.entity.StudentActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaStudentActivityRepository extends JpaRepository<StudentActivityEntity, java.util.UUID> {
    boolean existsByUserEntity_UserIdAndActivityEntity_ActivityId(String userId, String activityId);
}
