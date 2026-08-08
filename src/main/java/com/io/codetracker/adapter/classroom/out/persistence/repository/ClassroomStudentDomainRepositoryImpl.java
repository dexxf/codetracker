package com.io.codetracker.adapter.classroom.out.persistence.repository;


import java.util.UUID;
import com.io.codetracker.common.cache.CacheNames;
import com.io.codetracker.domain.classroom.repository.ClassroomStudentDomainRepository;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;
import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomStudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.Cacheable;

@Repository
@AllArgsConstructor
public class ClassroomStudentDomainRepositoryImpl implements ClassroomStudentDomainRepository {

    private final JpaClassroomStudentRepository classroomStudentRepository;

    @Override
    @Cacheable(value = CacheNames.CLASSROOM_STUDENT_MEMBERSHIP, key = "{#classroomId, #studentUserId}")
    public boolean existsByClassroomIdAndStudentUserId(UUID classroomId, UUID studentUserId) {
        return classroomStudentRepository.existsByClassroom_ClassroomIdAndStudentUserIdAndStatus(classroomId, studentUserId, StudentStatus.ACTIVE);
    }

    @Override
    @Cacheable(value = CacheNames.CLASSROOM_STUDENT_MEMBERSHIP, key = "#classroomId")
    public int countByClassroomId(UUID classroomId) {
        return classroomStudentRepository.countByClassroom_ClassroomIdAndStatus(classroomId, StudentStatus.ACTIVE);
    }
}

