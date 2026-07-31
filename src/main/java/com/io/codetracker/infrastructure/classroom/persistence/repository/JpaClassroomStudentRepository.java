package com.io.codetracker.infrastructure.classroom.persistence.repository;


import java.util.UUID;
import com.io.codetracker.domain.classroom.valueObject.ClassroomStatus;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;
import com.io.codetracker.application.classroom.result.ClassroomStudentJoinedData;
import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.io.codetracker.application.activity.result.StudentSummaryData;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaClassroomStudentRepository extends JpaRepository<ClassroomStudentEntity, Long> {
    boolean existsByClassroom_ClassroomIdAndStudentUserId(UUID classroomId, UUID studentUserId);
    boolean existsByClassroom_ClassroomIdAndStudentUserIdAndStatus(UUID classroomId, UUID studentUserId, StudentStatus status);
    Optional<ClassroomStudentEntity> findByClassroom_ClassroomIdAndStudentUserId(UUID classroomId, UUID studentUserId);

    @Query("SELECT cs FROM ClassroomStudentEntity cs JOIN cs.classroom c WHERE cs.studentUserId = :studentUserId AND (:studentStatus IS NULL OR cs.status = :studentStatus) AND (:classroomStatus IS NULL OR c.status = :classroomStatus)")
    List<ClassroomStudentEntity> findEnrollmentsByStatus(@Param("studentUserId") UUID studentUserId, @Param("studentStatus") StudentStatus studentStatus, @Param("classroomStatus") ClassroomStatus classroomStatus);

    int countByClassroom_ClassroomIdAndStatus(UUID classroomId, StudentStatus status);

    List<ClassroomStudentEntity> findByClassroom_ClassroomIdAndStatusOrderByJoinedAt(UUID classroomId, StudentStatus status);

    List<ClassroomStudentEntity> findByClassroom_ClassroomIdAndStatusOrderByJoinedAtDesc(UUID classroomId, StudentStatus status);

    @Query("""
            SELECT new com.io.codetracker.application.activity.result.StudentSummaryData(
                cs.studentUserId,
                u.firstName,
                u.lastName,
                u.profileUrl
            )
            FROM ClassroomStudentEntity cs
            JOIN UserEntity u ON u.userId = cs.studentUserId
            WHERE cs.classroom.classroomId = :classroomId
              AND cs.status = com.io.codetracker.domain.classroom.valueObject.StudentStatus.ACTIVE
            ORDER BY cs.joinedAt ASC
            """)
    List<StudentSummaryData> findStudentActivityInfoStudentsByClassroomId(@Param("classroomId") UUID classroomId);

    @Query("""
            SELECT new com.io.codetracker.application.classroom.result.ClassroomStudentJoinedData(
                cs.studentUserId,
                u.firstName,
                u.lastName,
                u.profileUrl,
                cs.joinedAt
            )
            FROM ClassroomStudentEntity cs
            JOIN UserEntity u ON u.userId = cs.studentUserId
            WHERE cs.classroom.classroomId = :classroomId
              AND cs.status = com.io.codetracker.domain.classroom.valueObject.StudentStatus.ACTIVE
            ORDER BY cs.joinedAt DESC
            """)
    List<ClassroomStudentJoinedData> findRecentStudentJoinedByClassroomId(@Param("classroomId") UUID classroomId, Pageable pageable);

    @Query("""
    SELECT COUNT(cs)
    FROM ClassroomStudentEntity cs
    WHERE cs.status = com.io.codetracker.domain.classroom.valueObject.StudentStatus.ACTIVE
      AND cs.classroom.classroomId = :classroomId
    """)
    long countByStatus_ActiveAndClassroom_ClassroomId(@Param("classroomId") UUID classroomId);
}

