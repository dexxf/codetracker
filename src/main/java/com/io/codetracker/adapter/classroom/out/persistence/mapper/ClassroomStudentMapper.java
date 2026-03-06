package com.io.codetracker.adapter.classroom.out.persistence.mapper;

import com.io.codetracker.domain.classroom.entity.ClassroomStudent;
import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomStudentEntity;

import java.util.Objects;

public final class ClassroomStudentMapper {

    private ClassroomStudentMapper() {
    }

    public static ClassroomStudentEntity toEntity(ClassroomStudent domain) {
        if (Objects.isNull(domain)) return null;

        ClassroomStudentEntity entity = new ClassroomStudentEntity();
        entity.setClassroomId(domain.getClassroomId());
        entity.setStudentUserId(domain.getStudentUserId());
        entity.setStatus(domain.getStatus());
        entity.setJoinedAt(domain.getJoinedAt());
        entity.setLastActiveAt(domain.getLastActiveAt());
        entity.setLeftAT(domain.getLeftAt());
        return entity;
    }

    public static ClassroomStudent toDomain(ClassroomStudentEntity entity) {
        if (Objects.isNull(entity)) return null;

        return new ClassroomStudent(
                entity.getClassroomId(),
                entity.getStudentUserId(),
                entity.getStatus(),
                entity.getJoinedAt(),
                entity.getLastActiveAt(),
                entity.getLeftAT()
        );
    }
}