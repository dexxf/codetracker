package com.io.codetracker.adapter.classroom.out.persistence.repository;

import com.io.codetracker.domain.activity.valueObject.ActivityStatus;
import org.springframework.stereotype.Repository;

import com.io.codetracker.application.classroom.port.out.ClassroomActivityAppPort;
import com.io.codetracker.domain.classroom.valueObject.ClassroomStatus;
import com.io.codetracker.infrastructure.activity.persistence.repository.JpaActivityRepository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class ClassroomActivityAppAdapter implements ClassroomActivityAppPort {
   
   private final JpaActivityRepository jpaActivityRepository;

    @Override
    public long countByClassroomId(String classroomId) {
        return jpaActivityRepository.countByClassroomId(classroomId);
    }

    @Override
    public long countActiveActivitiesByClassroomId(String classroomId) {
        return jpaActivityRepository.countByClassroomIdAndStatus(classroomId, ActivityStatus.PUBLISHED);
    }


}
