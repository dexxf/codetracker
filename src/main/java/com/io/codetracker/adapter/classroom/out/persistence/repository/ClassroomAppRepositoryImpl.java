package com.io.codetracker.adapter.classroom.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomRepository;
import org.springframework.stereotype.Repository;
import com.io.codetracker.application.classroom.port.out.ClassroomAppRepository;
import com.io.codetracker.domain.classroom.entity.Classroom;
import com.io.codetracker.domain.classroom.entity.ClassroomSettings;
import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomEntity;
import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomSettingsEntity;
import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomSettingsRepository;
import com.io.codetracker.adapter.classroom.out.persistence.mapper.ClassroomMapper;
import com.io.codetracker.adapter.classroom.out.persistence.mapper.ClassroomSettingsMapper;

@Repository
public class ClassroomAppRepositoryImpl implements ClassroomAppRepository {
    
    private final JpaClassroomRepository jpaClassroomRepository;
    private final JpaClassroomSettingsRepository jpaClassroomSettingsRepository;
    
    public ClassroomAppRepositoryImpl(JpaClassroomRepository jpaClassroomRepository,
                                      JpaClassroomSettingsRepository jpaClassroomSettingsRepository) {
        this.jpaClassroomRepository = jpaClassroomRepository;
        this.jpaClassroomSettingsRepository = jpaClassroomSettingsRepository;
    }
    
    @Override
    public void saveClassroom(Classroom classroom, ClassroomSettings classroomSettings) {
        ClassroomEntity entity = ClassroomMapper.toEntity(classroom);
        ClassroomSettingsEntity settingsEntity = ClassroomSettingsMapper.toEntity(classroomSettings);
        entity.setSettings(settingsEntity);
        jpaClassroomRepository.save(entity);
    }

    @Override
    public void deleteByClassroomId(String classroomId) {
        jpaClassroomRepository.deleteById(classroomId);
    }

    @Override
    public List<Classroom> findByInstructorUserId(String instructorUserId) {
        return jpaClassroomRepository.findByInstructorUserId(instructorUserId)
            .stream()
            .map(ClassroomMapper::toDomain)
            .toList();
    }

    @Override
    public List<Classroom> findAllById(List<String> classroomIds) {
        List<ClassroomEntity> entities = jpaClassroomRepository.findAllById(classroomIds);
        return entities.stream().map(ClassroomMapper::toDomain).toList();
    }

    @Override
    public Optional<Classroom> findByClassroomId(String classroomId) {
        return jpaClassroomRepository.findById(classroomId).map(ClassroomMapper::toDomain);
    }

    @Override
    public Optional<ClassroomSettings> findSettingsByClassroomId(String classroomId) {
        return jpaClassroomSettingsRepository.findByClassroomId(classroomId)
            .map(ClassroomSettingsMapper::toDomain);
    }

    @Override
    public boolean existsByClassroomId(String classroomId) {
        return jpaClassroomRepository.existsById(classroomId);
    }

    @Override
    public boolean existsByClassroomIdAndInstructorUserId(String classroomId, String instructorUserId) {
        return jpaClassroomRepository.existsByClassroomIdAndInstructorUserId(classroomId, instructorUserId);
    }

    @Override
    public Integer findMaxStudentByClassroomId(String classroomId) {
        return jpaClassroomSettingsRepository.findMaxStudentByClassroomId(classroomId);
    }
}