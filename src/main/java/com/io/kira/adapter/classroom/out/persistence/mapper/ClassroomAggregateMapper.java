package com.io.kira.adapter.classroom.out.persistence.mapper;

import com.io.kira.domain.classroom.aggregate.ClassroomAggregate;
import com.io.kira.infrastructure.classroom.persistence.entity.ClassroomEntity;
import com.io.kira.infrastructure.classroom.persistence.entity.ClassroomSettingsEntity;

public final class ClassroomAggregateMapper {

    private ClassroomAggregateMapper() {
    }

    public static ClassroomEntity toEntity(ClassroomAggregate aggregate) {
        ClassroomEntity entity = ClassroomMapper.toEntity(aggregate.classroom());
        ClassroomSettingsEntity settingsEntity = ClassroomSettingsMapper.toEntity(aggregate.settings());
        entity.setSettings(settingsEntity);
        return entity;
    }

    public static ClassroomAggregate toDomain(ClassroomEntity entity) {
        return new ClassroomAggregate(
                ClassroomMapper.toDomain(entity),
                ClassroomSettingsMapper.toDomain(entity.getSettings())
        );
    }
}
