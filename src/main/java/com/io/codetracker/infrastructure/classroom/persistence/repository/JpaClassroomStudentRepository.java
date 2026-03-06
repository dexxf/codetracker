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
    boolean existsByClassroomIdAndStudentUserId(String classroomId, String studentUserId);
    @Query("SELECT cs FROM ClassroomStudentEntity cs INNER JOIN ClassroomEntity c ON cs.classroomId = c.classroomId WHERE cs.studentUserId = :studentUserId AND (:studentStatus IS NULL OR cs.status = :studentStatus) AND (:classroomStatus IS NULL OR c.status = :classroomStatus)")
    List<ClassroomStudentEntity> findEnrollmentsByStatus(@Param("studentUserId") String studentUserId, @Param("studentStatus") StudentStatus studentStatus, @Param("classroomStatus") ClassroomStatus classroomStatus);
    int countByClassroomId(String classroomId);
}