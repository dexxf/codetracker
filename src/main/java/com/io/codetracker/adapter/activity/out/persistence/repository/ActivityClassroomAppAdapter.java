package com.io.codetracker.adapter.activity.out.persistence.repository;

import com.io.codetracker.application.activity.port.out.ActivityClassroomAppPort;
import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ActivityClassroomAppAdapter implements ActivityClassroomAppPort {

    JpaClassroomRepository jpa;

    @Override
    public boolean existsByClassroomId(String s) {
        return jpa.existsByClassroomId(s);
    }

    @Override
    public boolean existsByClassroomIdAndInstructorUserId(String classroomId, String userId) {
        return jpa.existsByClassroomIdAndInstructorUserId(classroomId, userId);
    }

}
