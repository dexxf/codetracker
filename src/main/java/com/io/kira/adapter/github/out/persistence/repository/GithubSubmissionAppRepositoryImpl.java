package com.io.kira.adapter.github.out.persistence.repository;

import com.io.kira.adapter.classroom.out.cache.ClassroomRecentActivityCacheVersion;
import com.io.kira.adapter.github.out.persistence.mapper.GithubSubmissionMapper;
import com.io.kira.application.github.port.out.GithubSubmissionAppRepository;
import com.io.kira.domain.github.entity.GithubSubmission;
import com.io.kira.infrastructure.activity.persistence.entity.StudentActivityEntity;
import com.io.kira.infrastructure.activity.persistence.repository.JpaStudentActivityRepository;
import com.io.kira.infrastructure.github.persistence.entity.GithubSubmissionEntity;
import com.io.kira.infrastructure.github.persistence.repository.JpaGithubSubmissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class GithubSubmissionAppRepositoryImpl implements GithubSubmissionAppRepository {

    private final JpaGithubSubmissionRepository jpaGithubSubmissionRepository;
    private final JpaStudentActivityRepository jpaStudentActivityRepository;
    private final ClassroomRecentActivityCacheVersion recentActivityCacheVersion;

    @Override
    public GithubSubmission save(GithubSubmission githubSubmission) {
        StudentActivityEntity studentActivityEntity = jpaStudentActivityRepository.findById(githubSubmission.getStudentActivityId())
                .orElseThrow(() -> new IllegalArgumentException("Student activity not found: " + githubSubmission.getStudentActivityId()));

        GithubSubmissionEntity entity = GithubSubmissionMapper.toEntity(githubSubmission);
        entity.setStudentActivity(studentActivityEntity);

        GithubSubmissionEntity savedEntity = jpaGithubSubmissionRepository.save(entity);
        recentActivityCacheVersion.invalidate(
                studentActivityEntity.getActivityEntity().getClassroomEntity().getClassroomId()
        );
        return GithubSubmissionMapper.toDomain(savedEntity);
    }
}
