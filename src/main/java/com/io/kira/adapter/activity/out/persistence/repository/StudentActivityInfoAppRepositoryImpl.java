package com.io.kira.adapter.activity.out.persistence.repository;

import com.io.kira.adapter.activity.out.cache.ActivityCacheNames;
import com.io.kira.application.activity.port.out.StudentActivityInfoAppRepository;
import com.io.kira.application.activity.result.StudentSubmissionDetailsData;
import com.io.kira.application.activity.result.StudentSummaryData;
import com.io.kira.infrastructure.activity.persistence.repository.JpaStudentActivityRepository;
import com.io.kira.infrastructure.classroom.persistence.repository.JpaClassroomStudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class StudentActivityInfoAppRepositoryImpl implements StudentActivityInfoAppRepository {

    private final JpaClassroomStudentRepository jpaClassroomStudentRepository;
    private final JpaStudentActivityRepository jpaStudentActivityRepository;

    @Override
    @Cacheable(value = ActivityCacheNames.ACTIVITY_INFO,
            key = "@activityInfoCacheKey.studentsByClassroomId(#classroomId)",
            unless = "#result.isEmpty()")
    public List<StudentSummaryData> findClassroomStudents(UUID classroomId) {
        return jpaClassroomStudentRepository.findStudentActivityInfoStudentsByClassroomId(classroomId);
    }

    @Override
    @Cacheable(value = ActivityCacheNames.ACTIVITY_INFO,
            key = "@activityInfoCacheKey.submissionsByClassroomId(#classroomId)",
            unless = "#result.isEmpty()")
    public List<StudentSubmissionDetailsData> findStudentActivityInfos(UUID classroomId) {
        return jpaStudentActivityRepository.findStudentActivityInfosByClassroomId(classroomId);
    }
}
