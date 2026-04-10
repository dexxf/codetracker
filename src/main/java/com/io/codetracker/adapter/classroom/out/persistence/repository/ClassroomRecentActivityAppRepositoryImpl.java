package com.io.codetracker.adapter.classroom.out.persistence.repository;

import com.io.codetracker.application.classroom.port.out.ClassroomRecentActivityAppRepository;
import com.io.codetracker.application.classroom.result.ClassroomActivityCreatedData;
import com.io.codetracker.application.classroom.result.ClassroomRecentActivityData;
import com.io.codetracker.application.classroom.result.ClassroomRepositorySubmissionData;
import com.io.codetracker.application.classroom.result.ClassroomStudentJoinedData;
import com.io.codetracker.infrastructure.activity.persistence.repository.JpaActivityRepository;
import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomStudentRepository;
import com.io.codetracker.infrastructure.github.persistence.repository.JpaGithubSubmissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
@AllArgsConstructor
public class ClassroomRecentActivityAppRepositoryImpl implements ClassroomRecentActivityAppRepository {

    private final JpaClassroomStudentRepository jpaClassroomStudentRepository;
    private final JpaGithubSubmissionRepository jpaGithubSubmissionRepository;
    private final JpaActivityRepository jpaActivityRepository;

    @Override
    public List<ClassroomRecentActivityData> findRecentActivities(String classroomId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);

        List<ClassroomStudentJoinedData> joinedData = jpaClassroomStudentRepository
                .findRecentStudentJoinedByClassroomId(classroomId, pageable);

        List<ClassroomRepositorySubmissionData> repositorySubmissionData = jpaGithubSubmissionRepository
                .findRecentRepositorySubmissionsByClassroomId(classroomId, pageable);

        List<ClassroomActivityCreatedData> activityCreatedData = jpaActivityRepository
                .findRecentCreatedActivitiesByClassroomId(classroomId, pageable);

        List<ClassroomRecentActivityData> allActivities = new ArrayList<>();
        allActivities.addAll(joinedData.stream().map(ClassroomRecentActivityData::fromStudentJoined).toList());
        allActivities.addAll(repositorySubmissionData.stream().map(ClassroomRecentActivityData::fromRepositorySubmitted).toList());
        allActivities.addAll(activityCreatedData.stream().map(ClassroomRecentActivityData::fromActivityCreated).toList());

        return allActivities.stream()
                .filter(activity -> activity.occurredAt() != null)
                .sorted(Comparator.comparing(ClassroomRecentActivityData::occurredAt).reversed())
                .limit(limit)
                .toList();
    }
}
