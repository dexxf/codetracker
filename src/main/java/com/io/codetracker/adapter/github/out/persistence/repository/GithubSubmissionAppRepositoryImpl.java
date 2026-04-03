package com.io.codetracker.adapter.github.out.persistence.repository;

import com.io.codetracker.adapter.github.out.persistence.mapper.GithubSubmissionMapper;
import com.io.codetracker.application.github.port.out.GithubSubmissionAppRepository;
import com.io.codetracker.domain.github.entity.GithubSubmission;
import com.io.codetracker.infrastructure.activity.persistence.entity.StudentActivityEntity;
import com.io.codetracker.infrastructure.activity.persistence.repository.JpaStudentActivityRepository;
import com.io.codetracker.infrastructure.github.persistence.entity.GithubSubmissionEntity;
import com.io.codetracker.infrastructure.github.persistence.repository.JpaGithubSubmissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@AllArgsConstructor
public class GithubSubmissionAppRepositoryImpl implements GithubSubmissionAppRepository {

    private final JpaGithubSubmissionRepository jpaGithubSubmissionRepository;
    private final JpaStudentActivityRepository jpaStudentActivityRepository;

    @Override
    public GithubSubmission save(GithubSubmission githubSubmission) {
        StudentActivityEntity studentActivityEntity = jpaStudentActivityRepository.findById(UUID.fromString(githubSubmission.getStudentActivityId()))
                .orElseThrow(() -> new IllegalArgumentException("Student activity not found: " + githubSubmission.getStudentActivityId()));

        GithubSubmissionEntity entity = GithubSubmissionMapper.toEntity(githubSubmission);
        entity.setStudentActivity(studentActivityEntity);

        GithubSubmissionEntity savedEntity = jpaGithubSubmissionRepository.save(entity);
        return GithubSubmissionMapper.toDomain(savedEntity);
    }
}