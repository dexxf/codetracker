package com.io.codetracker.domain.activity.repository;

public interface ActivityDomainRepository {
    boolean existsById(String id);
    boolean existsByClassroomIdAndActivityId(String classroomId, String activityId);
}
