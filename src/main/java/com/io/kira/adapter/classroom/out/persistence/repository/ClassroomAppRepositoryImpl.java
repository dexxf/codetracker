package com.io.kira.adapter.classroom.out.persistence.repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.io.kira.common.cache.CacheNames;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.io.kira.adapter.classroom.out.persistence.mapper.ClassroomAggregateMapper;
import com.io.kira.adapter.classroom.out.persistence.mapper.ClassroomMapper;
import com.io.kira.adapter.classroom.out.persistence.mapper.ClassroomSettingsMapper;
import com.io.kira.application.classroom.port.out.ClassroomAppRepository;
import com.io.kira.domain.classroom.aggregate.ClassroomAggregate;
import com.io.kira.domain.classroom.entity.ClassroomSettings;
import com.io.kira.infrastructure.classroom.persistence.entity.ClassroomEntity;
import com.io.kira.infrastructure.classroom.persistence.entity.ClassroomSettingsEntity;
import com.io.kira.infrastructure.classroom.persistence.repository.JpaClassroomRepository;
import com.io.kira.infrastructure.classroom.persistence.repository.JpaClassroomSettingsRepository;

@Repository
public class ClassroomAppRepositoryImpl implements ClassroomAppRepository {
    
    private final JpaClassroomRepository jpaClassroomRepository;
    private final JpaClassroomSettingsRepository jpaClassroomSettingsRepository;
    
    public ClassroomAppRepositoryImpl(
            JpaClassroomRepository jpaClassroomRepository,
            JpaClassroomSettingsRepository jpaClassroomSettingsRepository
    ) {
        this.jpaClassroomRepository = jpaClassroomRepository;
        this.jpaClassroomSettingsRepository = jpaClassroomSettingsRepository;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheNames.CLASSROOM, allEntries = true),
            @CacheEvict(value = CacheNames.CLASSROOM_BY_ID, key = "#aggregate.classroom.classroomId"),
            @CacheEvict(value = CacheNames.CLASSROOM_BY_CODE, key = "#aggregate.classroom.classCode"),
            @CacheEvict(value = CacheNames.CLASSROOM_SETTINGS, key = "#aggregate.classroom.classroomId")
    })
    public void save(ClassroomAggregate aggregate) {
        jpaClassroomRepository.save(ClassroomAggregateMapper.toEntity(aggregate));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheNames.CLASSROOM, allEntries = true),
            @CacheEvict(value = CacheNames.CLASSROOM_BY_ID, key = "#aggregate.classroom.classroomId"),
            @CacheEvict(value = CacheNames.CLASSROOM_BY_CODE, key = "#aggregate.classroom.classCode"),
            @CacheEvict(value = CacheNames.CLASSROOM_SETTINGS, key = "#aggregate.classroom.classroomId")
    })
    public void update(ClassroomAggregate aggregate) {
        ClassroomEntity entity = jpaClassroomRepository.findById(aggregate.classroom().getClassroomId())
                .orElseThrow(() -> new RuntimeException("Classroom not found"));
        ClassroomMapper.updateEntity(aggregate.classroom(), entity);
        ClassroomSettingsEntity settingsEntity = entity.getSettings();
        if (settingsEntity != null) {
            ClassroomSettingsMapper.updateEntity(aggregate.settings(), settingsEntity);
        } else {
            entity.setSettings(ClassroomSettingsMapper.toEntity(aggregate.settings()));
        }
        jpaClassroomRepository.save(entity);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheNames.CLASSROOM, allEntries = true),
            @CacheEvict(value = CacheNames.CLASSROOM_BY_ID, key = "#classroomId"),
            @CacheEvict(value = CacheNames.CLASSROOM_BY_CODE, allEntries = true),
            @CacheEvict(value = CacheNames.CLASSROOM_SETTINGS, key = "#classroomId")
    })
    public void deleteByClassroomId(UUID classroomId) {
        jpaClassroomRepository.deleteById(classroomId);
    }

    @Override
    @Cacheable(
            value = CacheNames.CLASSROOM,
            key = "#instructorUserId",
            unless = "#result.isEmpty()"
    )
    public List<ClassroomAggregate> findByInstructorUserId(UUID instructorUserId) {
        return jpaClassroomRepository.findByInstructorUserId(instructorUserId)
            .stream()
            .map(ClassroomAggregateMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassroomAggregate> findAllById(List<UUID> classroomIds) {
        List<ClassroomEntity> entities = jpaClassroomRepository.findAllById(classroomIds);
        return entities.stream().map(ClassroomAggregateMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = CacheNames.CLASSROOM_BY_ID, key = "#classroomId", unless = "#result == null")
    public Optional<ClassroomAggregate> findByClassroomId(UUID classroomId) {
        return jpaClassroomRepository.findById(classroomId).map(ClassroomAggregateMapper::toDomain);
    }

    @Override
    @Cacheable(value = CacheNames.CLASSROOM_SETTINGS, key = "#classroomId", unless = "#result == null")
    public Optional<ClassroomSettings> findSettingsByClassroomId(UUID classroomId) {
        return jpaClassroomSettingsRepository.findByClassroomId(classroomId)
            .map(ClassroomSettingsMapper::toDomain);
    }

    @Override
    public boolean existsByClassroomId(UUID classroomId) {
        return jpaClassroomRepository.existsById(classroomId);
    }

    @Override
    public boolean existsByClassroomIdAndInstructorUserId(UUID classroomId, UUID instructorUserId) {
        return jpaClassroomRepository.existsByClassroomIdAndInstructorUserId(classroomId, instructorUserId);
    }
}
