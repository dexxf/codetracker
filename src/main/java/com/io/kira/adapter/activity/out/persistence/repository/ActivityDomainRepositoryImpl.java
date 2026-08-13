package com.io.kira.adapter.activity.out.persistence.repository;

import com.io.kira.adapter.activity.out.cache.ActivityCacheNames;
import com.io.kira.adapter.activity.out.persistence.mapper.ActivityMapper;
import com.io.kira.domain.activity.entity.Activity;
import com.io.kira.domain.activity.repository.ActivityDomainRepository;
import com.io.kira.infrastructure.activity.persistence.repository.JpaActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.Cacheable;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class ActivityDomainRepositoryImpl implements ActivityDomainRepository {

    private final JpaActivityRepository jpa;

    @Override
    public boolean existsByClassroomIdAndActivityId(UUID classroomId, String activityId) {
        return jpa.existsByClassroomEntity_ClassroomIdAndActivityId(classroomId, activityId);
    }

    @Override
    @Cacheable(value = ActivityCacheNames.ACTIVITY,
            key = "@activityCacheKey.byId(#activityId)", unless = "#result == null")
    public Optional<Activity> findByActivityId(String activityId) {
        return jpa.findById(activityId).map(ActivityMapper::toDomain);
    }

    @Override
    public boolean existsById(String id) {
        return jpa.existsById(id);
    }
}
