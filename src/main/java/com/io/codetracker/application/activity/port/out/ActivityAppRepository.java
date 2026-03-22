package com.io.codetracker.application.activity.port.out;

import com.io.codetracker.domain.activity.entity.Activity;

import java.util.List;
import java.util.Optional;

public interface ActivityAppRepository {
    Activity save(Activity data);
    List<Activity> findByClassroomId(String classroomId, String instructorId);
    Optional<Activity> findById(String activityId);
    void deleteByActivityId(String activityId);
    void update(Activity updatedActivity);
}
