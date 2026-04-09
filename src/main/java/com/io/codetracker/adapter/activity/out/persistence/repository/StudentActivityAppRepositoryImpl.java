package com.io.codetracker.adapter.activity.out.persistence.repository;

import com.io.codetracker.application.activity.port.out.StudentActivityAppRepository;
import com.io.codetracker.domain.activity.entity.StudentActivity;
import com.io.codetracker.infrastructure.activity.persistence.entity.ActivityEntity;
import com.io.codetracker.infrastructure.activity.persistence.entity.StudentActivityEntity;
import com.io.codetracker.infrastructure.activity.persistence.repository.JpaActivityRepository;
import com.io.codetracker.infrastructure.activity.persistence.repository.JpaStudentActivityRepository;
import com.io.codetracker.infrastructure.user.persistence.entity.UserEntity;
import com.io.codetracker.infrastructure.user.persistence.repository.JpaUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class StudentActivityAppRepositoryImpl implements StudentActivityAppRepository {
    private final JpaStudentActivityRepository jpaStudentActivityRepository;
    private final JpaUserRepository jpaUserRepository;
    private final JpaActivityRepository jpaActivityRepository;

    @Override
    public boolean existsSubmission(String userId, String activityId) {
        return jpaStudentActivityRepository.existsByUserEntity_UserIdAndActivityEntity_ActivityId(userId, activityId);
    }

    @Override
    public boolean existsByUserId(String userId) {
        return jpaUserRepository.existsById(userId);
    }

    @Override
    public Optional<StudentActivity> findByUserIdAndActivityId(String userId, String activityId) {
        return jpaStudentActivityRepository.findByUserEntity_UserIdAndActivityEntity_ActivityId(userId, activityId)
                .map(savedEntity -> new StudentActivity(
                        savedEntity.getStudentActivityId().toString(),
                        savedEntity.getActivityEntity().getActivityId(),
                        savedEntity.getUserEntity().getUserId(),
                        savedEntity.getSubmissionStatus(),
                        savedEntity.getFeedback()
                ));
    }

    @Override
    public StudentActivity save(StudentActivity studentActivity) {
        ActivityEntity activityEntity = jpaActivityRepository.findById(studentActivity.getActivityId())
                .orElseThrow(() -> new IllegalArgumentException("Activity not found: " + studentActivity.getActivityId()));
        UserEntity userEntity = jpaUserRepository.findById(studentActivity.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + studentActivity.getUserId()));

        StudentActivityEntity entity;
        if (studentActivity.getStudentActivityId() != null && !studentActivity.getStudentActivityId().isBlank()) {
            UUID studentActivityId = UUID.fromString(studentActivity.getStudentActivityId());
            entity = jpaStudentActivityRepository.findById(studentActivityId)
                    .orElseThrow(() -> new IllegalArgumentException("Student activity not found: " + studentActivity.getStudentActivityId()));
        } else {
            entity = new StudentActivityEntity();
        }

        entity.setActivityEntity(activityEntity);
        entity.setUserEntity(userEntity);
        entity.setSubmissionStatus(studentActivity.getSubmissionStatus());
        entity.setFeedback(studentActivity.getFeedback());

        StudentActivityEntity savedEntity = jpaStudentActivityRepository.save(entity);
        return new StudentActivity(
                savedEntity.getStudentActivityId().toString(),
                savedEntity.getActivityEntity().getActivityId(),
                savedEntity.getUserEntity().getUserId(),
                savedEntity.getSubmissionStatus(),
                savedEntity.getFeedback()
        );
    }
}
