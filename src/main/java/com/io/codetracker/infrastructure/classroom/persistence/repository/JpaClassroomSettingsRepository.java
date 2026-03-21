package com.io.codetracker.infrastructure.classroom.persistence.repository;

import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomSettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaClassroomSettingsRepository extends JpaRepository<ClassroomSettingsEntity, String> {
    Optional<ClassroomSettingsEntity> findByClassroomId(String classroomId);
    @Query("SELECT c.maxStudents FROM ClassroomSettingsEntity c WHERE c.classroomId = :classroomId")
    Integer findMaxStudentByClassroomId(@Param("classroomId") String classroomId);
}