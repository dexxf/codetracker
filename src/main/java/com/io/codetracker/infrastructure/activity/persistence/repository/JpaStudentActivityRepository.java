package com.io.codetracker.infrastructure.activity.persistence.repository;

import com.io.codetracker.infrastructure.activity.persistence.entity.StudentActivityEntity;
import com.io.codetracker.application.activity.result.SubmittedActivityData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface JpaStudentActivityRepository extends JpaRepository<StudentActivityEntity, java.util.UUID> {
    boolean existsByUserEntity_UserIdAndActivityEntity_ActivityId(String userId, String activityId);

    @Query("SELECT sa.activityEntity.activityId FROM StudentActivityEntity sa WHERE sa.activityEntity.classroomEntity.classroomId = :classroomId AND sa.userEntity.userId = :userId")
    Set<String> findActivityIdsByClassroomIdAndUserId(@Param("classroomId") String classroomId,@Param("userId") String userId);

    @Query("""
            SELECT new com.io.codetracker.application.activity.result.SubmittedActivityData(
                sa.userEntity.userId,sa.studentActivityId,sa.activityEntity.activityId,sa.activityEntity.title,sa.activityEntity.description,sa.createdAt,sa.updatedAt,
                gs.repositoryOwnerUsername,gs.repositoryId, gs.repositoryName,gs.mode,gs.repositoryUrl, gs.submittedAt)
            FROM StudentActivityEntity sa LEFT JOIN sa.githubSubmission gs WHERE sa.activityEntity.classroomEntity.classroomId = :classroomId
            ORDER BY sa.createdAt DESC
            """)
    List<SubmittedActivityData> findSubmittedActivitiesByClassroomId(@Param("classroomId") String classroomId);
}
