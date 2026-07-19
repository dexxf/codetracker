package com.io.codetracker.adapter.activity.out.persistence.repository;

import com.io.codetracker.application.activity.port.out.StudentActivityInfoAppRepository;
import com.io.codetracker.application.activity.result.StudentActivityInfoData;
import com.io.codetracker.application.activity.result.StudentActivityInfoStudentData;
import com.io.codetracker.infrastructure.activity.persistence.repository.JpaStudentActivityRepository;
import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomStudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class StudentActivityInfoAppRepositoryImpl implements StudentActivityInfoAppRepository {

    private final JpaClassroomStudentRepository jpaClassroomStudentRepository;
    private final JpaStudentActivityRepository jpaStudentActivityRepository;

    @Override
    public List<StudentActivityInfoStudentData> findClassroomStudents(UUID classroomId) {
        return jpaClassroomStudentRepository.findStudentActivityInfoStudentsByClassroomId(classroomId);
    }

    @Override
    public List<StudentActivityInfoData> findStudentActivityInfos(UUID classroomId) {
        return jpaStudentActivityRepository.findStudentActivityInfosByClassroomId(classroomId);
    }
}
