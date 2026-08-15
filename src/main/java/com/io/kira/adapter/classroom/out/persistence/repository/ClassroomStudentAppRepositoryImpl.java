package com.io.kira.adapter.classroom.out.persistence.repository;


import java.util.*;
import java.util.stream.Collectors;

import com.io.kira.adapter.classroom.out.cache.ClassroomCacheNames;
import com.io.kira.adapter.classroom.out.cache.ClassroomStudentCacheEvictor;
import com.io.kira.adapter.classroom.out.persistence.mapper.ClassroomStudentMapper;
import com.io.kira.application.classroom.port.out.ClassroomStudentAppRepository;
import com.io.kira.domain.classroom.entity.ClassroomStudent;
import com.io.kira.domain.classroom.valueObject.ClassroomStatus;
import com.io.kira.domain.classroom.valueObject.StudentStatus;
import com.io.kira.infrastructure.classroom.persistence.entity.ClassroomEntity;
import com.io.kira.infrastructure.classroom.persistence.entity.ClassroomStudentEntity;
import com.io.kira.infrastructure.classroom.persistence.repository.JpaClassroomRepository;
import com.io.kira.infrastructure.classroom.persistence.repository.JpaClassroomStudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;


@Repository
@AllArgsConstructor
public class ClassroomStudentAppRepositoryImpl implements ClassroomStudentAppRepository {

    private final JpaClassroomStudentRepository jpaClassroomStudentRepository;
    private final JpaClassroomRepository jpaClassroomRepository;
    private final ClassroomStudentCacheEvictor cacheEvictor;

    @Override
    @CacheEvict(value = ClassroomCacheNames.CLASSROOM_RECENT_ACTIVITY, allEntries = true)
    public boolean save(ClassroomStudent classroomStudent) {

        Optional<ClassroomEntity> classroomEntityOpt = jpaClassroomRepository.findByClassroomId((classroomStudent.getClassroomId()));
        if (classroomEntityOpt.isEmpty()) return false;
        ClassroomEntity classroomEntity = classroomEntityOpt.get();

        ClassroomStudentEntity entity = jpaClassroomStudentRepository.findByClassroom_ClassroomIdAndStudentUserId(
                classroomStudent.getClassroomId(),
                classroomStudent.getStudentUserId()
        ).orElseGet(ClassroomStudentEntity::new);

        entity.setStudentUserId(classroomStudent.getStudentUserId());
        entity.setStatus(classroomStudent.getStatus());
        entity.setJoinedAt(classroomStudent.getJoinedAt());
        entity.setLastActiveAt(classroomStudent.getLastActiveAt());
        entity.setLeftAt(classroomStudent.getLeftAt());
        entity.setClassroom(classroomEntity);

        classroomEntity.addStudent(entity);
        jpaClassroomStudentRepository.save(entity);
        cacheEvictor.evictFor(classroomStudent.getClassroomId(), classroomStudent.getStudentUserId());
        return true;
    }

    @Override
    public boolean existsByClassroomIdAndStudentUserId(UUID classroomId, UUID studentUserId) {
        return jpaClassroomStudentRepository.existsByClassroom_ClassroomIdAndStudentUserIdAndStatus(
                classroomId,
                studentUserId,
                StudentStatus.ACTIVE
        );
    }

    @Override
    @Cacheable(value = ClassroomCacheNames.CLASSROOM_STUDENT, key = "@classroomStudentCacheKey.activeEnrollmentsByUserId(#studentUserId)", unless = "#result.isEmpty()")
    public List<ClassroomStudent> findActiveEnrollmentsWithActiveClassroom(UUID studentUserId) {
        List<ClassroomStudentEntity> entities = jpaClassroomStudentRepository
                .findEnrollmentsByStatus(studentUserId, StudentStatus.ACTIVE, ClassroomStatus.ACTIVE);
        return entities.stream()
                .map(ClassroomStudentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Map<UUID, Long> countActiveClassroomStudentByClassroomIds(List<UUID> classroomIds) {
        Map<UUID, Long> countMap = new HashMap<>();
        for (UUID classroomId : classroomIds) {
            Long count = jpaClassroomStudentRepository.countByStatus_ActiveAndClassroom_ClassroomId(classroomId);
            countMap.put(classroomId, count);
        }
        return countMap;
    }

    @Override
    @Cacheable(value = ClassroomCacheNames.CLASSROOM_STUDENT, key = "@classroomStudentCacheKey.byClassroomIdAndStatusAndOrder(#classroomId, #status, #ascending)", unless = "#result.isEmpty()")
    public List<ClassroomStudent> findClassroomStudents(UUID classroomId, StudentStatus status, boolean ascending) {
        return ascending
                ? mapToDomain(jpaClassroomStudentRepository.findByClassroom_ClassroomIdAndStatusOrderByJoinedAt(classroomId, status))
                : mapToDomain(jpaClassroomStudentRepository.findByClassroom_ClassroomIdAndStatusOrderByJoinedAtDesc(classroomId, status));
    }

    private List<ClassroomStudent> mapToDomain(List<ClassroomStudentEntity> entities) {
        return entities.stream().map(ClassroomStudentMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = ClassroomCacheNames.CLASSROOM_STUDENT, key = "@classroomStudentCacheKey.activeCountByClassroomId(#classroomId)")
    public long countActiveClassroomStudentByClassroomId(UUID classroomId) {
        return jpaClassroomStudentRepository.countByStatus_ActiveAndClassroom_ClassroomId(classroomId);
    }

    @Override
    @Cacheable(value = ClassroomCacheNames.CLASSROOM_STUDENT, key = "@classroomStudentCacheKey.byClassroomIdAndUserId(#classroomId, #studentUserId)")
    public Optional<ClassroomStudent> findByClassroomIdAndStudentUserId(UUID classroomId, UUID studentUserId) {
        return jpaClassroomStudentRepository.findByClassroom_ClassroomIdAndStudentUserId(classroomId, studentUserId)
                .map(ClassroomStudentMapper::toDomain);
    }

}

