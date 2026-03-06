package com.io.codetracker.adapter.classroom.out.persistence.repository;

import com.io.codetracker.adapter.classroom.out.persistence.mapper.ClassroomStudentMapper;
import com.io.codetracker.application.classroom.port.out.ClassroomStudentAppRepository;
import com.io.codetracker.domain.classroom.entity.ClassroomStudent;
import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomStudentEntity;
import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomStudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ClassroomStudentAppRepositoryImpl implements ClassroomStudentAppRepository {

    private final JpaClassroomStudentRepository jpaClassroomStudentRepository;

    @Override
    public boolean save(ClassroomStudent classroomStudent) {
        ClassroomStudentEntity entity = ClassroomStudentMapper.toEntity(classroomStudent);
        if(entity == null) return false;

        jpaClassroomStudentRepository.save(entity);
        return true;
    }

}