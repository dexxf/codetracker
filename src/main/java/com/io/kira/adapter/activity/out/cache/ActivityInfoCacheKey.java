package com.io.kira.adapter.activity.out.cache;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component("activityInfoCacheKey")
public final class ActivityInfoCacheKey {

    public String studentsByClassroomId(UUID classroomId) {
        return "students-by-classroom-id:" + classroomId;
    }

    public String submissionsByClassroomId(UUID classroomId) {
        return "submissions-by-classroom-id:" + classroomId;
    }
}
