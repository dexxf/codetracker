package com.io.kira.domain.activity.repository;

import com.io.kira.domain.activity.entity.Activity;

import java.util.Optional;
import java.util.UUID;

public interface ActivityDomainRepository {
    boolean existsById(String id);
    boolean existsByClassroomIdAndActivityId(UUID classroomId, String activityId);
    Optional<Activity> findByActivityId(String activityId);
}
