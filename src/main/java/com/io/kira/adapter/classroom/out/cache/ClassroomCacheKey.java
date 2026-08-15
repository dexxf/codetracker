package com.io.kira.adapter.classroom.out.cache;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component("classroomCacheKey")
public final class ClassroomCacheKey {

    public String byId(UUID classroomId) {
        return "by-id:" + classroomId;
    }

    public String byCode(String classCode) {
        return "by-code:" + classCode;
    }

    public String byInstructorUserId(UUID instructorUserId) {
        return "by-instructor-user-id:" + instructorUserId;
    }

    public String recentActivities(UUID classroomId, long version, int limit) {
        return "recent-activities:" + classroomId + ":" + version + ":" + limit;
    }

    public String activityCounts(UUID classroomId) {
        return "activity-counts:" + classroomId;
    }

    public String activeActivityCounts(UUID classroomId) {
        return "active-activity-counts:" + classroomId;
    }
}
