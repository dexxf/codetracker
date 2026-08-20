package com.io.kira.infrastructure.activity.persistence.repository;


import java.util.UUID;
import com.io.kira.infrastructure.activity.persistence.entity.StudentActivityEntity;
import com.io.kira.application.activity.result.StudentSubmissionDetailsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface JpaStudentActivityRepository extends JpaRepository<StudentActivityEntity, UUID> {
    boolean existsByUserEntity_UserIdAndActivityEntity_ActivityId(UUID userId, UUID activityId);
    Optional<StudentActivityEntity> findByUserEntity_UserIdAndActivityEntity_ActivityId(UUID userId, UUID activityId);

    @Query("SELECT sa.activityEntity.activityId FROM StudentActivityEntity sa WHERE sa.activityEntity.classroomEntity.classroomId = :classroomId AND sa.userEntity.userId = :userId")
    Set<String> findActivityIdsByClassroomIdAndUserId(@Param("classroomId") UUID classroomId,@Param("userId") UUID userId);

    @Query("""
            SELECT new com.io.kira.application.activity.result.StudentSubmissionDetailsData(
                sa.userEntity.userId,sa.studentActivityId,sa.activityEntity.activityId,sa.activityEntity.title,sa.activityEntity.description,sa.activityEntity.maxScore,sa.createdAt,sa.updatedAt,
                gs.repositoryOwnerUsername,gs.repositoryId, gs.repositoryName,gs.mode,gs.repositoryUrl, gs.submittedAt,
                sa.submittedCommitSha, sa.submissionStatus, sa.feedback, sa.score)
            FROM StudentActivityEntity sa LEFT JOIN sa.githubSubmission gs WHERE sa.activityEntity.classroomEntity.classroomId = :classroomId
            ORDER BY sa.createdAt DESC
            """)
    List<StudentSubmissionDetailsData> findStudentActivityInfosByClassroomId(@Param("classroomId") UUID classroomId);
}

