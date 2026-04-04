package com.io.codetracker.infrastructure.activity.persistence.repository;

import com.io.codetracker.domain.activity.valueObject.ActivityStatus;
import com.io.codetracker.infrastructure.activity.persistence.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaActivityRepository extends JpaRepository<ActivityEntity, String> {
    boolean existsByClassroomEntity_ClassroomIdAndActivityId(String classroomId, String activityId);
    List<ActivityEntity> findByClassroomEntity_ClassroomIdAndClassroomEntity_InstructorUserId(String classroomId, String instructorUserId);
    long countByClassroomEntity_ClassroomIdAndStatus(String classroomId, ActivityStatus status);
    long countByClassroomEntity_ClassroomId(String classroomId);

    @Query("SELECT a FROM ActivityEntity a WHERE a.classroomEntity.classroomId = :classroomId")
    List<ActivityEntity> findActivitiesByClassroomId(@Param("classroomId") String classroomId);
}