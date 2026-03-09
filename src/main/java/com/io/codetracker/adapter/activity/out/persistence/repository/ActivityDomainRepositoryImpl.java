package com.io.codetracker.adapter.activity.out.persistence.repository;

import com.io.codetracker.adapter.activity.out.persistence.mapper.ActivityMapper;
import com.io.codetracker.domain.activity.entity.Activity;
import com.io.codetracker.domain.activity.repository.ActivityDomainRepository;
import com.io.codetracker.infrastructure.activity.persistence.repository.JpaActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ActivityDomainRepositoryImpl implements ActivityDomainRepository {

    private final JpaActivityRepository jpa;

    @Override
    public boolean existsByClassroomIdAndActivityId(String classroomId, String activityId) {
        return jpa.existsByClassroomIdAndActivityId(classroomId, activityId);
    }

    @Override
    public Optional<Activity> findByActivityId(String activityId) {
        return jpa.findById(activityId).map(ActivityMapper::toDomain);
    }

    @Override
    public boolean existsById(String id) {
        return jpa.existsById(id);
    }
}
