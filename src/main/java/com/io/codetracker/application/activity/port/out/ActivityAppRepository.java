package com.io.codetracker.application.activity.port.out;

import com.io.codetracker.application.activity.result.StudentActivityViewData;
import com.io.codetracker.domain.activity.entity.Activity;

import java.util.List;
import java.util.Optional;

public interface ActivityAppRepository {
    Activity save(Activity data);
    List<Activity> findActivitiesByClassroomIdAndInstructorUserId(String classroomId, String instructorId);
    Optional<Activity> findById(String activityId);
    void deleteByActivityId(String activityId);
    void update(Activity updatedActivity);
    List<StudentActivityViewData> findStudentActivities(String classroomId, String userId);
}
