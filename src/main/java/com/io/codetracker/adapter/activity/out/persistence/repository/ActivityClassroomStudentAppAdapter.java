package com.io.codetracker.adapter.activity.out.persistence.repository;

import com.io.codetracker.application.activity.port.out.ActivityClassroomStudentAppPort;
import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomStudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ActivityClassroomStudentAppAdapter implements ActivityClassroomStudentAppPort {

    private final JpaClassroomStudentRepository jpaClassroomStudentRepository;


    @Override
    public boolean existsByClassroomIdAndStudentUserId(String classroomId, String studentUserId) {
        return jpaClassroomStudentRepository.existsByClassroom_ClassroomIdAndStudentUserId(classroomId, studentUserId);
    }
}
