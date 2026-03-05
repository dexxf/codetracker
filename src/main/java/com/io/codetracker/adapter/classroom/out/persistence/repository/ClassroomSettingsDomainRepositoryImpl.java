package com.io.codetracker.adapter.classroom.out.persistence.repository;

import com.io.codetracker.adapter.classroom.out.persistence.mapper.ClassroomSettingsMapper;
import com.io.codetracker.domain.classroom.entity.ClassroomSettings;
import com.io.codetracker.domain.classroom.repository.ClassroomSettingsDomainRepository;
import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomSettingsEntity;
import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomSettingsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ClassroomSettingsDomainRepositoryImpl implements ClassroomSettingsDomainRepository {

    private final JpaClassroomSettingsRepository jpaClassroomSettingsRepository;

    @Override
    public Optional<ClassroomSettings> findByClassroomId(String classroomId) {
        Optional<ClassroomSettingsEntity> classroomSettingsEntity = jpaClassroomSettingsRepository.findByClassroomId(classroomId);
        return classroomSettingsEntity.map(ClassroomSettingsMapper::toDomain);
    }
}
