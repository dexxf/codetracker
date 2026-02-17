package com.io.codetracker.adapter.classroom.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.io.codetracker.domain.classroom.valueObject.ClassroomStatus;
import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomEntity;

public interface JpaClassroomRepository extends JpaRepository<ClassroomEntity, String> {
    boolean existsByClassCodeAndStatusNot(String classCode, ClassroomStatus status);
    List<ClassroomEntity> findByInstructorUserId(String instructorUserId);
}