package com.io.codetracker.infrastructure.classroom.persistence.repository;

import com.io.codetracker.domain.classroom.valueObject.ClassroomStatus;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;
import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaClassroomStudentRepository extends JpaRepository<ClassroomStudentEntity, Long> {
    boolean existsByClassroom_ClassroomIdAndStudentUserId(String classroomId, String studentUserId);
    boolean existsByClassroom_ClassroomIdAndStudentUserIdAndStatus(String classroomId, String studentUserId, StudentStatus status);

    @Query("SELECT cs FROM ClassroomStudentEntity cs JOIN cs.classroom c WHERE cs.studentUserId = :studentUserId AND (:studentStatus IS NULL OR cs.status = :studentStatus) AND (:classroomStatus IS NULL OR c.status = :classroomStatus)")
    List<ClassroomStudentEntity> findEnrollmentsByStatus(@Param("studentUserId") String studentUserId, @Param("studentStatus") StudentStatus studentStatus, @Param("classroomStatus") ClassroomStatus classroomStatus);

    int countByClassroom_ClassroomId(String classroomId);

    List<ClassroomStudentEntity> findByClassroom_ClassroomIdAndStatusOrderByJoinedAt(String classroomId, StudentStatus status);

    List<ClassroomStudentEntity> findByClassroom_ClassroomIdAndStatusOrderByJoinedAtDesc(String classroomId, StudentStatus status);
}