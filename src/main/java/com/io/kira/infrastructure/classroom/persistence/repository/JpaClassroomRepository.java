package com.io.kira.infrastructure.classroom.persistence.repository;


import java.util.UUID;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.io.kira.infrastructure.classroom.persistence.entity.ClassroomEntity;

public interface JpaClassroomRepository extends JpaRepository<ClassroomEntity, UUID> {
    boolean existsByClassCode(String classCode);
    List<ClassroomEntity> findByInstructorUserId(UUID instructorUserId);
    Optional<ClassroomEntity> findByClassCode(String classCode);
    boolean existsByClassroomId(UUID classroomId);
    boolean existsByClassroomIdAndInstructorUserId(UUID classroomId, UUID instructorUserId);

    Optional<ClassroomEntity> findByClassroomId(UUID classroomId);
}
