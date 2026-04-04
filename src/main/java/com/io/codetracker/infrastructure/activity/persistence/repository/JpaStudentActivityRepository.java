package com.io.codetracker.infrastructure.activity.persistence.repository;

import com.io.codetracker.infrastructure.activity.persistence.entity.StudentActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface JpaStudentActivityRepository extends JpaRepository<StudentActivityEntity, java.util.UUID> {
    boolean existsByUserEntity_UserIdAndActivityEntity_ActivityId(String userId, String activityId);

    @Query("SELECT sa.activityEntity.activityId FROM StudentActivityEntity sa WHERE sa.activityEntity.classroomEntity.classroomId = :classroomId AND sa.userEntity.userId = :userId")
    Set<String> findActivityIdsByClassroomIdAndUserId(@Param("classroomId") String classroomId,@Param("userId") String userId);
}
