package com.io.codetracker.infrastructure.classroom.persistence.repository;

import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JpaClassroomStudentRepository extends JpaRepository<ClassroomStudentEntity, Long> {
    boolean existsByClassroomIdAndStudentUserId(String classroomId, String studentUserId);
}