package com.io.kira.adapter.classroom.out.persistence.repository;

import com.io.kira.adapter.classroom.out.cache.ClassroomCacheNames;
import com.io.kira.application.classroom.port.out.ClassroomRecentActivityAppRepository;
import com.io.kira.application.classroom.result.ClassroomActivityCreatedData;
import com.io.kira.application.classroom.result.ClassroomRecentActivityData;
import com.io.kira.application.classroom.result.ClassroomRepositorySubmissionData;
import com.io.kira.application.classroom.result.ClassroomStudentJoinedData;
import com.io.kira.infrastructure.activity.persistence.repository.JpaActivityRepository;
import com.io.kira.infrastructure.classroom.persistence.repository.JpaClassroomStudentRepository;
import com.io.kira.infrastructure.github.persistence.repository.JpaGithubSubmissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class ClassroomRecentActivityAppRepositoryImpl implements ClassroomRecentActivityAppRepository {

    private final JpaClassroomStudentRepository jpaClassroomStudentRepository;
    private final JpaGithubSubmissionRepository jpaGithubSubmissionRepository;
    private final JpaActivityRepository jpaActivityRepository;

    @Override
    @Cacheable(
            value = ClassroomCacheNames.CLASSROOM_RECENT_ACTIVITY,
            key = "@classroomCacheKey.recentActivities(#classroomId, #limit)",
            unless = "#result.isEmpty()"
    )
    public List<ClassroomRecentActivityData> findRecentActivities(UUID classroomId, int limit) {
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
                .collect(Collectors.toList());
    }
}
