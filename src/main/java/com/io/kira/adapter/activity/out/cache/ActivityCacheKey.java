package com.io.kira.adapter.activity.out.cache;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component("activityCacheKey")
public final class ActivityCacheKey {

    public String byClassroomIdAndInstructorUserId(UUID classroomId, UUID instructorUserId) {
        return "by-classroom-id-and-instructor-user-id:" + classroomId + ":" + instructorUserId;
    }

    public String byId(String activityId) {
        return "by-id:" + activityId;
    }

    public String studentActivitiesByClassroomIdAndUserId(UUID classroomId, UUID userId) {
        return "student-activities-by-classroom-id-and-user-id:" + classroomId + ":" + userId;
    }

    public String unsubmittedRepositoryActivityByClassroomIdAndUserId(UUID classroomId, UUID userId) {
        return "unsubmitted-repository-activity-by-classroom-id-and-user-id:" + classroomId + ":" + userId;
    }

    public String existsByClassroomIdAndActivityId(UUID classroomId, String activityId) {
        return "exists-by-classroom-id-and-activity-id:" + classroomId + ":" + activityId;
    }

    public String maxScoreByClassroomIdAndActivityId(UUID classroomId, String activityId) {
        return "max-score-by-classroom-id-and-activity-id:" + classroomId + ":" + activityId;
    }
}
