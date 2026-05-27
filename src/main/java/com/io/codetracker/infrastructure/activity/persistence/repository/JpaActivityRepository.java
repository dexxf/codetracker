package com.io.codetracker.infrastructure.activity.persistence.repository;

import com.io.codetracker.application.activity.result.StudentActivityViewData;
import com.io.codetracker.application.classroom.result.ClassroomActivityCreatedData;
import com.io.codetracker.domain.activity.valueObject.ActivityStatus;
import com.io.codetracker.infrastructure.activity.persistence.entity.ActivityEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaActivityRepository extends JpaRepository<ActivityEntity, String> {
    boolean existsByClassroomEntity_ClassroomIdAndActivityId(String classroomId, String activityId);
    List<ActivityEntity> findByClassroomEntity_ClassroomIdAndClassroomEntity_InstructorUserId(String classroomId, String instructorUserId);

    @Query("""
    SELECT new com.io.codetracker.application.activity.result.StudentActivityViewData(
        a.activityId,
        a.title,
        a.description,
        a.maxScore,
        a.dueDate,
        sa.studentActivityId,
        gs.repositoryName,
        gs.repositoryUrl,
        gs.mode,
        gs.submittedAt,
        sa.submissionStatus,
        sa.score,
        sa.feedback,
        a.status
    )
    FROM StudentActivityEntity sa
    JOIN sa.activityEntity a
    LEFT JOIN sa.githubSubmission gs
    WHERE a.classroomEntity.classroomId = :classroomId
      AND sa.userEntity.userId = :userId
    """)
    List<StudentActivityViewData> findStudentActivityViewsByClassroomIdAndUserId(
            @Param("classroomId") String classroomId,
            @Param("userId") String userId
    );

    long countByClassroomEntity_ClassroomIdAndStatus(String classroomId, ActivityStatus status);
    long countByClassroomEntity_ClassroomId(String classroomId);

        @Query("""
                        SELECT a.maxScore
                        FROM ActivityEntity a
                        WHERE a.classroomEntity.classroomId = :classroomId
                            AND a.activityId = :activityId
                        """)
        Optional<Integer> findMaxScoreByClassroomIdAndActivityId(@Param("classroomId") String classroomId,
                                                                                                                            @Param("activityId") String activityId);

    @Query("SELECT a FROM ActivityEntity a WHERE a.classroomEntity.classroomId = :classroomId")
    List<ActivityEntity> findActivitiesByClassroomId(@Param("classroomId") String classroomId);

    @Query("""
            SELECT new com.io.codetracker.application.classroom.result.ClassroomActivityCreatedData(
                a.activityId,
                a.title,
                a.createdAt
            )
            FROM ActivityEntity a
            WHERE a.classroomEntity.classroomId = :classroomId
            ORDER BY a.createdAt DESC
            """)
    List<ClassroomActivityCreatedData> findRecentCreatedActivitiesByClassroomId(@Param("classroomId") String classroomId, Pageable pageable);
}
