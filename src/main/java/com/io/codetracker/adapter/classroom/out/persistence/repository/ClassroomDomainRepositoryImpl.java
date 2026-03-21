package com.io.codetracker.adapter.classroom.out.persistence.repository;

import com.io.codetracker.adapter.classroom.out.persistence.mapper.ClassroomMapper;
import com.io.codetracker.domain.classroom.entity.Classroom;
import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomEntity;
import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomRepository;
import org.springframework.stereotype.Repository;

import com.io.codetracker.domain.classroom.repository.ClassroomDomainRepository;

import lombok.AllArgsConstructor;

import java.util.Optional;


@Repository
@AllArgsConstructor
public class ClassroomDomainRepositoryImpl implements ClassroomDomainRepository {

    private final JpaClassroomRepository jpaClassroomRepository;

    @Override
    public boolean existsByClassroomId(String classroomId) {
        return jpaClassroomRepository.existsById(classroomId);
    }

    @Override
    public boolean existsByActiveCode(String code) {    
        return jpaClassroomRepository.existsByClassCode(code);
    }

    @Override
    public Optional<Classroom> findByClassroomId(String classroomId) {
        return jpaClassroomRepository.findById(classroomId).map(ClassroomMapper::toDomain);
    }

    @Override
    public Optional<Classroom> findByClassCode(String classCode) {
        Optional<ClassroomEntity> classroomEntity = jpaClassroomRepository.findByClassCode(classCode);
        return classroomEntity.map(ClassroomMapper::toDomain);
    }

}
