package com.io.codetracker.adapter.activity.out.persistence.repository;

import com.io.codetracker.adapter.activity.out.persistence.mapper.ActivityMapper;
import com.io.codetracker.application.activity.port.out.ActivityGithubSubmissionAppPort;
import com.io.codetracker.application.activity.result.ActivityData;
import com.io.codetracker.infrastructure.activity.persistence.entity.ActivityEntity;
import com.io.codetracker.infrastructure.activity.persistence.repository.JpaActivityRepository;
import com.io.codetracker.infrastructure.activity.persistence.repository.JpaStudentActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@AllArgsConstructor
public class ActivityGithubSubmissionAppAdapter implements ActivityGithubSubmissionAppPort {

    private final JpaStudentActivityRepository jpaStudentActivityRepository;
    private final JpaActivityRepository jpaActivityRepository;

    @Override
    public List<ActivityData> getUnsubmittedRepositoryActivity(String classroomId, String userId) {
        Set<String> submittedActivityIds = jpaStudentActivityRepository.findActivityIdsByClassroomIdAndUserId(classroomId,userId);

        List<ActivityEntity> activityEntityList = jpaActivityRepository.findActivitiesByClassroomId(classroomId);

        if(activityEntityList.isEmpty())
            return List.of();

        return activityEntityList.stream()
                .filter(activity -> !submittedActivityIds.contains(activity.getActivityId()))
                .map(ActivityMapper::toDomain)
                .map(ActivityData::from)
                .toList();
    }
}
