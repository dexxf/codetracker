package com.io.codetracker.adapter.classroom.out.persistence.repository;


import org.springframework.stereotype.Repository;

import com.io.codetracker.domain.classroom.repository.ClassroomDomainRepository;
import com.io.codetracker.domain.classroom.valueObject.ClassroomStatus;

import lombok.AllArgsConstructor;


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
        return jpaClassroomRepository.existsByClassCodeAndStatusNot(code, ClassroomStatus.INACTIVE);
    }
    
}
