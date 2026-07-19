package com.io.codetracker.application.activity.port.out;


import java.util.UUID;
import com.io.codetracker.application.activity.result.StudentActivityViewData;
import com.io.codetracker.domain.activity.entity.Activity;

import java.util.List;
import java.util.Optional;

public interface ActivityAppRepository {
    Activity save(Activity data);
    List<Activity> findActivitiesByClassroomIdAndInstructorUserId(UUID classroomId, UUID instructorId);
    Optional<Activity> findById(String activityId);
    void deleteByActivityId(String activityId);
    void update(Activity updatedActivity);
    List<StudentActivityViewData> findStudentActivities(UUID classroomId, UUID userId);
}

