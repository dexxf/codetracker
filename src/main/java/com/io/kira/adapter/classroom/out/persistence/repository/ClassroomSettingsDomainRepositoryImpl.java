package com.io.kira.adapter.classroom.out.persistence.repository;

import com.io.kira.adapter.classroom.out.cache.ClassroomCacheNames;
import com.io.kira.adapter.classroom.out.persistence.mapper.ClassroomSettingsMapper;
import com.io.kira.domain.classroom.entity.ClassroomSettings;
import com.io.kira.domain.classroom.repository.ClassroomSettingsDomainRepository;
import com.io.kira.infrastructure.classroom.persistence.entity.ClassroomSettingsEntity;
import com.io.kira.infrastructure.classroom.persistence.repository.JpaClassroomSettingsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.Cacheable;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class ClassroomSettingsDomainRepositoryImpl implements ClassroomSettingsDomainRepository {

    private final JpaClassroomSettingsRepository jpaClassroomSettingsRepository;

    @Override
    @Cacheable(value = ClassroomCacheNames.CLASSROOM_SETTINGS, key = "#classroomId", unless = "#result == null")
    public Optional<ClassroomSettings> findByClassroomId(UUID classroomId) {
        Optional<ClassroomSettingsEntity> classroomSettingsEntity = jpaClassroomSettingsRepository.findByClassroomId(classroomId);
        return classroomSettingsEntity.map(ClassroomSettingsMapper::toDomain);
    }
}
