package com.io.codetracker.adapter.classroom.out.persistence.repository;

import com.io.codetracker.domain.classroom.repository.ClassroomStudentDomainRepository;
import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomStudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ClassroomStudentDomainRepositoryImpl implements ClassroomStudentDomainRepository {

    private final JpaClassroomStudentRepository classroomStudentRepository;

    @Override
    public boolean existsByClassroomIdAndStudentUserId(String classroomId, String studentUserId) {
        return classroomStudentRepository.existsByClassroom_ClassroomIdAndStudentUserId(classroomId, studentUserId);
    }

    @Override
    public int countByClassroomId(String classroomId) {
        return classroomStudentRepository.countByClassroom_ClassroomId(classroomId);
    }
}
