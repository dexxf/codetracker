package com.io.codetracker.domain.activity.repository;

import com.io.codetracker.domain.activity.entity.Activity;

import java.util.Optional;

public interface ActivityDomainRepository {
    boolean existsById(String id);
    boolean existsByClassroomIdAndActivityId(String classroomId, String activityId);
    Optional<Activity> findByActivityId(String activityId);
}
