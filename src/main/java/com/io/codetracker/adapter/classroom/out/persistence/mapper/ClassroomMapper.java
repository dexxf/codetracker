package com.io.codetracker.adapter.classroom.out.persistence.mapper;

import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomEntity;
import com.io.codetracker.domain.classroom.entity.Classroom;

public final class ClassroomMapper {
    
    private ClassroomMapper() {}
    
    public static ClassroomEntity toEntity(Classroom domain) {
        if(domain == null) {
        throw new IllegalArgumentException("Classroom domain object cannot be null");
        }
        
        ClassroomEntity entity = new ClassroomEntity();
        entity.setClassroomId(domain.getClassroomId());
        entity.setInstructorUserId(domain.getInstructorUserId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setClassCode(domain.getClassCode());
        entity.setStatus(domain.getStatus());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
    
    public static Classroom toDomain(ClassroomEntity entity) {
        if(entity == null) {
            throw new IllegalArgumentException("Classroom entity cannot be null");
        }
        
        return new Classroom(
            entity.getClassroomId(),
            entity.getInstructorUserId(),
            entity.getName(),
            entity.getDescription(),
            entity.getClassCode(),
            entity.getStatus(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    public static void updateEntity(Classroom domain, ClassroomEntity entity) {
        if (domain == null || entity == null) {
            throw new IllegalArgumentException("Neither domain nor entity can be null");
        }
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setStatus(domain.getStatus());
        entity.setUpdatedAt(domain.getUpdatedAt());
    }
}