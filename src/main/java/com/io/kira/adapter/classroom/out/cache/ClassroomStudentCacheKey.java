package com.io.kira.adapter.classroom.out.cache;

import java.util.List;
import java.util.UUID;

import com.io.kira.domain.classroom.valueObject.StudentStatus;
import org.springframework.stereotype.Component;

@Component("classroomStudentCacheKey")
public final class ClassroomStudentCacheKey {

    public String activeEnrollmentsByUserId(UUID userId) {
        return "active-enrollments-by-user-id:" + userId;
    }

    public String activeCountsByClassroomIds(List<UUID> classroomIds) {
        return "active-counts-by-classroom-ids:" + classroomIds;
    }

    public String byClassroomIdAndStatusAndOrder(UUID classroomId, StudentStatus status, boolean ascending) {
        return "by-classroom-id-and-status-and-order:" + classroomId + ":" + status + ":" + ascending;
    }

    public String byClassroomIdAndStatusAndOrder(UUID classroomId, String status, boolean ascending) {
        return "by-classroom-id-and-status-and-order:" + classroomId + ":" + status + ":" + ascending;
    }

    public String activeCountByClassroomId(UUID classroomId) {
        return "active-count-by-classroom-id:" + classroomId;
    }

    public String byClassroomIdAndUserId(UUID classroomId, UUID userId) {
        return "by-classroom-id-and-user-id:" + classroomId + ":" + userId;
    }

    public String existsByClassroomIdAndUserId(UUID classroomId, UUID userId) {
        return "exists-by-classroom-id-and-user-id:" + classroomId + ":" + userId;
    }

    public String countByClassroomId(UUID classroomId) {
        return "count-by-classroom-id:" + classroomId;
    }
}
