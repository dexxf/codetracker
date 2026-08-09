package com.io.kira.adapter.activity.out.persistence.repository;


import java.util.UUID;
import com.io.kira.application.activity.port.out.ActivityClassroomStudentAppPort;
import com.io.kira.infrastructure.classroom.persistence.repository.JpaClassroomStudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ActivityClassroomStudentAppAdapter implements ActivityClassroomStudentAppPort {

    private final JpaClassroomStudentRepository jpaClassroomStudentRepository;


    @Override
    public boolean existsByClassroomIdAndStudentUserId(UUID classroomId, UUID studentUserId) {
        return jpaClassroomStudentRepository.existsByClassroom_ClassroomIdAndStudentUserId(classroomId, studentUserId);
    }
}

