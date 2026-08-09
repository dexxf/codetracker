package com.io.kira.adapter.activity.out.persistence.repository;

import com.io.kira.application.activity.port.out.StudentActivityInfoAppRepository;
import com.io.kira.application.activity.result.StudentSubmissionDetailsData;
import com.io.kira.application.activity.result.StudentSummaryData;
import com.io.kira.infrastructure.activity.persistence.repository.JpaStudentActivityRepository;
import com.io.kira.infrastructure.classroom.persistence.repository.JpaClassroomStudentRepository;
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
    public List<StudentSummaryData> findClassroomStudents(UUID classroomId) {
        return jpaClassroomStudentRepository.findStudentActivityInfoStudentsByClassroomId(classroomId);
    }

    @Override
    public List<StudentSubmissionDetailsData> findStudentActivityInfos(UUID classroomId) {
        return jpaStudentActivityRepository.findStudentActivityInfosByClassroomId(classroomId);
    }
}
