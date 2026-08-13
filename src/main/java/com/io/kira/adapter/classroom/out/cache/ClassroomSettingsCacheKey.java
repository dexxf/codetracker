package com.io.kira.adapter.classroom.out.cache;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component("classroomSettingsCacheKey")
public final class ClassroomSettingsCacheKey {

    public String byClassroomId(UUID classroomId) {
        return "by-classroom-id:" + classroomId;
    }
}
