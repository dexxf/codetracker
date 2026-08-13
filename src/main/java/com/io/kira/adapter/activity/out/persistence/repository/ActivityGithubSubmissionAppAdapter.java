package com.io.kira.adapter.activity.out.persistence.repository;


import java.util.UUID;
import com.io.kira.adapter.activity.out.cache.ActivityCacheNames;
import com.io.kira.adapter.activity.out.persistence.mapper.ActivityMapper;
import com.io.kira.application.activity.port.out.ActivityGithubSubmissionAppPort;
import com.io.kira.application.activity.result.ActivityDetailsData;
import com.io.kira.infrastructure.activity.persistence.entity.ActivityEntity;
import com.io.kira.infrastructure.activity.persistence.repository.JpaActivityRepository;
import com.io.kira.infrastructure.activity.persistence.repository.JpaStudentActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Set;

@Repository
@AllArgsConstructor
public class ActivityGithubSubmissionAppAdapter implements ActivityGithubSubmissionAppPort {

    private final JpaStudentActivityRepository jpaStudentActivityRepository;
    private final JpaActivityRepository jpaActivityRepository;

    @Override
    @Cacheable(value = ActivityCacheNames.ACTIVITY,
            key = "@activityCacheKey.unsubmittedRepositoryActivityByClassroomIdAndUserId(#classroomId, #userId)",
            unless = "#result.isEmpty()")
    public List<ActivityDetailsData> getUnsubmittedRepositoryActivity(UUID classroomId, UUID userId) {
        Set<String> submittedActivityIds = jpaStudentActivityRepository.findActivityIdsByClassroomIdAndUserId(classroomId,userId);

        List<ActivityEntity> activityEntityList = jpaActivityRepository.findActivitiesByClassroomId(classroomId);

        if(activityEntityList.isEmpty())
            return List.of();

        return activityEntityList.stream()
                .filter(activity -> !submittedActivityIds.contains(activity.getActivityId()))
                .map(ActivityMapper::toDomain)
                .map(ActivityDetailsData::from)
                .toList();
    }
}

