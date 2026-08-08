package com.io.codetracker.adapter.classroom.out.persistence.repository;

import com.io.codetracker.adapter.classroom.out.persistence.mapper.ClassroomMapper;
import com.io.codetracker.common.cache.CacheNames;
import com.io.codetracker.domain.classroom.entity.Classroom;
import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomEntity;
import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.Cacheable;

import com.io.codetracker.domain.classroom.repository.ClassroomDomainRepository;

import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;


@Repository
@AllArgsConstructor
public class ClassroomDomainRepositoryImpl implements ClassroomDomainRepository {

    private final JpaClassroomRepository jpaClassroomRepository;

    @Override
    public boolean existsByClassroomId(UUID classroomId) {
        return jpaClassroomRepository.existsById(classroomId);
    }

    @Override
    public boolean existsByActiveCode(String code) {    
        return jpaClassroomRepository.existsByClassCode(code);
    }

    @Override
    @Cacheable(value = CacheNames.CLASSROOM_BY_ID, key = "#classroomId", unless = "#result == null")
    public Optional<Classroom> findByClassroomId(UUID classroomId) {
        return jpaClassroomRepository.findById(classroomId).map(ClassroomMapper::toDomain);
    }

    @Override
    @Cacheable(value = CacheNames.CLASSROOM_BY_CODE, key = "#classCode", unless = "#result.isEmpty()")
    public Optional<Classroom> findByClassCode(String classCode) {
        Optional<ClassroomEntity> classroomEntity = jpaClassroomRepository.findByClassCode(classCode);
        return classroomEntity.map(ClassroomMapper::toDomain);
    }

}
