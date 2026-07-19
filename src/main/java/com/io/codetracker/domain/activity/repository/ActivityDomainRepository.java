package com.io.codetracker.domain.activity.repository;

import com.io.codetracker.domain.activity.entity.Activity;

import java.util.Optional;
import java.util.UUID;

public interface ActivityDomainRepository {
    boolean existsById(String id);
    boolean existsByClassroomIdAndActivityId(UUID classroomId, String activityId);
    Optional<Activity> findByActivityId(String activityId);
}
