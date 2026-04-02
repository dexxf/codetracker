package com.io.codetracker.infrastructure.classroom.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaClassroomRepository extends JpaRepository<ClassroomEntity, String> {
    boolean existsByClassCode(String classCode);
    List<ClassroomEntity> findByInstructorUserId(String instructorUserId);
    Optional<ClassroomEntity> findByClassCode(String classCode);
    int countByClassroomId(String classroomId);
    boolean existsByClassroomId(String classroomId);
    boolean existsByClassroomIdAndInstructorUserId(String classroomId, String instructorUserId);
    @Query("SELECT ce.instructorUserId FROM ClassroomEntity ce WHERE  ce.classroomId = :classroomId")
    String findInstructorUserIdByClassroomId(@Param("classroomId") String classroomId);
}