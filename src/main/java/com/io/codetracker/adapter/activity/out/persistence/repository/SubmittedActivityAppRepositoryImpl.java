package com.io.codetracker.adapter.activity.out.persistence.repository;

import com.io.codetracker.application.activity.port.out.SubmittedActivityAppRepository;
import com.io.codetracker.application.activity.result.SubmittedActivityData;
import com.io.codetracker.application.activity.result.SubmittedActivityStudentData;
import com.io.codetracker.infrastructure.activity.persistence.repository.JpaStudentActivityRepository;
import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomStudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class SubmittedActivityAppRepositoryImpl implements SubmittedActivityAppRepository {

    private final JpaClassroomStudentRepository jpaClassroomStudentRepository;
    private final JpaStudentActivityRepository jpaStudentActivityRepository;

    @Override
    public List<SubmittedActivityStudentData> findClassroomStudents(String classroomId) {
        return jpaClassroomStudentRepository.findSubmittedActivityStudentsByClassroomId(classroomId);
    }

    @Override
    public List<SubmittedActivityData> findSubmittedActivities(String classroomId) {
        return jpaStudentActivityRepository.findSubmittedActivitiesByClassroomId(classroomId);
    }
}
