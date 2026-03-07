package com.io.codetracker.adapter.activity.out.persistence.repository;

import com.io.codetracker.adapter.activity.out.persistence.mapper.ActivityMapper;
import com.io.codetracker.application.activity.port.out.ActivityAppRepository;
import com.io.codetracker.domain.activity.entity.Activity;
import com.io.codetracker.infrastructure.activity.persistence.repository.JpaActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ActivityAppRepositoryImpl implements ActivityAppRepository {

    private final JpaActivityRepository jpa;

    @Override
    public Activity save(Activity data) {
            var entity = jpa.save(ActivityMapper.toEntity(data));
            return ActivityMapper.toDomain(entity);
    }

    @Override
    public List<Activity> findByClassroomId(String classroomId, String instructorId) {
        return jpa.findByClassroomIdAndCreatedByProfessorId(classroomId, instructorId).stream().map(
                ActivityMapper::toDomain
        ).toList();
    }
}
