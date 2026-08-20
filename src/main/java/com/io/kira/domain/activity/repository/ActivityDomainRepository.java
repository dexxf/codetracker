package com.io.kira.domain.activity.repository;

import com.io.kira.domain.activity.entity.Activity;

import java.util.Optional;
import java.util.UUID;

public interface ActivityDomainRepository {
    boolean existsByClassroomIdAndActivityId(UUID classroomId, UUID activityId);
    Optional<Activity> findByActivityId(UUID activityId);
}
