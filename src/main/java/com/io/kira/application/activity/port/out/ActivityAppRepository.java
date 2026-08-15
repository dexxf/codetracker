package com.io.kira.application.activity.port.out;


import java.util.UUID;
import com.io.kira.application.activity.result.StudentActivityOverviewData;
import com.io.kira.domain.activity.entity.Activity;

import java.util.List;
import java.util.Optional;

public interface ActivityAppRepository {
    Activity save(Activity data);
    List<Activity> findActivitiesByClassroomIdAndInstructorUserId(UUID classroomId, UUID instructorId);
    Optional<Activity> findById(String activityId);
    Activity deleteByActivityId(String activityId);
    void update(Activity updatedActivity);
    List<StudentActivityOverviewData> findStudentActivities(UUID classroomId, UUID userId);
}

