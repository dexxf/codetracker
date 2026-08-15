package com.io.kira.adapter.announcement.out.cache;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component("announcementCacheKey")
public final class AnnouncementCacheKey {

    public String byId(UUID announcementId) {
        return "by-id:" + announcementId;
    }

    public String byClassroomId(UUID classroomId) {
        return "by-classroom-id:" + classroomId;
    }
}
