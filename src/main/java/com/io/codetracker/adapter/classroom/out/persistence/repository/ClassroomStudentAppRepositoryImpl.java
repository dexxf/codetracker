package com.io.codetracker.adapter.classroom.out.persistence.repository;

import com.io.codetracker.adapter.classroom.out.persistence.mapper.ClassroomStudentMapper;
import com.io.codetracker.application.classroom.port.out.ClassroomStudentAppRepository;
import com.io.codetracker.domain.classroom.entity.ClassroomStudent;
import com.io.codetracker.domain.classroom.valueObject.ClassroomStatus;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;
import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomEntity;
import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomStudentEntity;
import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomRepository;
import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomStudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
@AllArgsConstructor
public class ClassroomStudentAppRepositoryImpl implements ClassroomStudentAppRepository {

    private final JpaClassroomStudentRepository jpaClassroomStudentRepository;
    private final JpaClassroomRepository jpaClassroomRepository;

    @Override
    public boolean save(ClassroomStudent classroomStudent) {
        ClassroomEntity classroomEntity = jpaClassroomRepository.findById(classroomStudent.getClassroomId())
                .orElse(null);
        if (classroomEntity == null) return false;

        ClassroomStudentEntity entity = ClassroomStudentMapper.toEntity(classroomStudent);
        classroomEntity.addStudent(entity);
        jpaClassroomStudentRepository.save(entity);
        return true;
    }

    @Override
    public List<ClassroomStudent> findActiveEnrollmentsWithActiveClassroom(String studentUserId) {
        List<ClassroomStudentEntity> entities = jpaClassroomStudentRepository
                .findEnrollmentsByStatus(studentUserId, StudentStatus.ACTIVE, ClassroomStatus.ACTIVE);
        return entities.stream()
                .map(ClassroomStudentMapper::toDomain)
                .toList();
    }

    @Override
    public Map<String, Integer> countByClassroomIds(List<String> classroomIds) {
        Map<String, Integer> countMap = new HashMap<>();
        for (String classroomId : classroomIds) {
            int count = jpaClassroomStudentRepository.countByClassroom_ClassroomId(classroomId);
            countMap.put(classroomId, count);
        }
        return countMap;
    }

    @Override
    public List<ClassroomStudent> findClassroomStudents(String classroomId, StudentStatus status, boolean ascending) {
        return ascending
                ? mapToDomain(jpaClassroomStudentRepository.findByClassroom_ClassroomIdAndStatusOrderByJoinedAt(classroomId, status))
                : mapToDomain(jpaClassroomStudentRepository.findByClassroom_ClassroomIdAndStatusOrderByJoinedAtDesc(classroomId, status));
    }

    private List<ClassroomStudent> mapToDomain(List<ClassroomStudentEntity> entities) {
        return entities.stream().map(ClassroomStudentMapper::toDomain).toList();
    }

    @Override
    public long countByClassroomId(String classroomId) {
        return jpaClassroomStudentRepository.countByClassroom_ClassroomId(classroomId);
    }

}