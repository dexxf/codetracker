package com.io.codetracker.adapter.classroom.out.persistence.repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.io.codetracker.adapter.classroom.out.persistence.mapper.ClassroomAggregateMapper;
import com.io.codetracker.adapter.classroom.out.persistence.mapper.ClassroomMapper;
import com.io.codetracker.adapter.classroom.out.persistence.mapper.ClassroomSettingsMapper;
import com.io.codetracker.application.classroom.port.out.ClassroomAppRepository;
import com.io.codetracker.domain.classroom.aggregate.ClassroomAggregate;
import com.io.codetracker.domain.classroom.entity.Classroom;
import com.io.codetracker.domain.classroom.entity.ClassroomSettings;
import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomEntity;
import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomSettingsEntity;
import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomRepository;
import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomSettingsRepository;

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
    public void save(ClassroomAggregate aggregate) {
        jpaClassroomRepository.save(ClassroomAggregateMapper.toEntity(aggregate));
    }

    @Override
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
    public void deleteByClassroomId(UUID classroomId) {
        jpaClassroomRepository.deleteById(classroomId);
    }

    @Override
    public List<Classroom> findByInstructorUserId(UUID instructorUserId) {
        return jpaClassroomRepository.findByInstructorUserId(instructorUserId)
            .stream()
            .map(ClassroomMapper::toDomain)
            .toList();
    }

    @Override
    public List<Classroom> findAllById(List<UUID> classroomIds) {
        List<ClassroomEntity> entities = jpaClassroomRepository.findAllById(classroomIds);
        return entities.stream().map(ClassroomMapper::toDomain).toList();
    }

    @Override
    public Optional<Classroom> findByClassroomId(UUID classroomId) {
        return jpaClassroomRepository.findById(classroomId).map(ClassroomMapper::toDomain);
    }

    @Override
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

    @Override
    public Integer findMaxStudentByClassroomId(UUID classroomId) {
        return jpaClassroomSettingsRepository.findMaxStudentByClassroomId(classroomId);
    }
}
