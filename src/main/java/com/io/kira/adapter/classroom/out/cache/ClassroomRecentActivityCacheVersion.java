package com.io.kira.adapter.classroom.out.cache;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("classroomRecentActivityCacheVersion")
public final class ClassroomRecentActivityCacheVersion {

    private static final String KEY_PREFIX = "cache-version:classroom-recent-activity:";

    private final StringRedisTemplate redisTemplate;

    public ClassroomRecentActivityCacheVersion(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public long get(UUID classroomId) {
        String value = redisTemplate.opsForValue().get(key(classroomId));
        return value == null ? 0L : Long.parseLong(value);
    }

    public void invalidate(UUID classroomId) {
        redisTemplate.opsForValue().increment(key(classroomId));
    }

    private String key(UUID classroomId) {
        return KEY_PREFIX + classroomId;
    }
}
