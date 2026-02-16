package com.io.codetracker.adapter.classroom.out.persistence.repository;

import org.springframework.stereotype.Repository;
import com.io.codetracker.application.classroom.port.out.ClassroomAppRepository;
import com.io.codetracker.domain.classroom.entity.Classroom;
import com.io.codetracker.domain.classroom.entity.ClassroomSettings;
import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomEntity;
import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomSettingsEntity;
import com.io.codetracker.adapter.classroom.out.persistence.mapper.ClassroomMapper;
import com.io.codetracker.adapter.classroom.out.persistence.mapper.ClassroomSettingsMapper;

@Repository
public class ClassroomAppRepositoryImpl implements ClassroomAppRepository {
    
    private final JpaClassroomRepository jpaClassroomRepository;
    
    public ClassroomAppRepositoryImpl(JpaClassroomRepository jpaClassroomRepository) {
        this.jpaClassroomRepository = jpaClassroomRepository;
    }
    
    @Override
    public void saveClassroom(Classroom classroom, ClassroomSettings classroomSettings) {
        ClassroomEntity entity = ClassroomMapper.toEntity(classroom);
        ClassroomSettingsEntity settingsEntity = ClassroomSettingsMapper.toEntity(classroomSettings);
        entity.setSettings(settingsEntity);
        jpaClassroomRepository.save(entity);
    }
    
 
}