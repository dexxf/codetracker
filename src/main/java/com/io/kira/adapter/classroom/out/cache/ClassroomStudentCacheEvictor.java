package com.io.kira.adapter.classroom.out.cache;

import com.io.kira.domain.classroom.valueObject.StudentStatus;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public final class ClassroomStudentCacheEvictor {

    private final Cache cache;
    private final ClassroomStudentCacheKey cacheKey;

    public ClassroomStudentCacheEvictor(CacheManager cacheManager, ClassroomStudentCacheKey cacheKey) {
        this.cache = cacheManager.getCache(ClassroomCacheNames.CLASSROOM_STUDENT);
        this.cacheKey = cacheKey;
    }

    public void evictFor(UUID classroomId, UUID studentUserId) {
        evict(cacheKey.existsByClassroomIdAndUserId(classroomId, studentUserId));
        evict(cacheKey.countByClassroomId(classroomId));
        evict(cacheKey.activeEnrollmentsByUserId(studentUserId));
        evict(cacheKey.activeCountByClassroomId(classroomId));
        evict(cacheKey.byClassroomIdAndUserId(classroomId, studentUserId));

        for (StudentStatus status : StudentStatus.values()) {
            evict(cacheKey.byClassroomIdAndStatusAndOrder(classroomId, status, true));
            evict(cacheKey.byClassroomIdAndStatusAndOrder(classroomId, status, false));
        }
    }

    private void evict(Object key) {
        if (cache != null) {
            cache.evict(key);
        }
    }
}
