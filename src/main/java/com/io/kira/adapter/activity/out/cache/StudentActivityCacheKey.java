package com.io.kira.adapter.activity.out.cache;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component("studentActivityCacheKey")
public final class StudentActivityCacheKey {

    public String byUserIdAndActivityId(UUID userId, String activityId) {
        return "by-user-id-and-activity-id:" + userId + ":" + activityId;
    }

    public String repositoryUrlByUserIdAndActivityId(UUID userId, String activityId) {
        return "repository-url-by-user-id-and-activity-id:" + userId + ":" + activityId;
    }
}
