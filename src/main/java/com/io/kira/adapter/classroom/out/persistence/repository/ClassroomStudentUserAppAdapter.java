package com.io.kira.adapter.classroom.out.persistence.repository;


import java.util.UUID;
import com.io.kira.adapter.user.out.persistence.mapper.UserMapper;
import com.io.kira.infrastructure.user.persistence.repository.JpaUserRepository;
import com.io.kira.application.classroom.port.out.ClassroomStudentUserAppPort;
import com.io.kira.application.classroom.result.ClassroomStudentData;
import com.io.kira.domain.classroom.entity.ClassroomStudent;
import com.io.kira.domain.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Repository
@AllArgsConstructor
public class ClassroomStudentUserAppAdapter implements ClassroomStudentUserAppPort {

    private final JpaUserRepository jpa;

    @Override
    public List<ClassroomStudentData> addUserData(List<ClassroomStudent> classroomStudents) {
        List<UUID> userIds = classroomStudents.stream()
                .map(ClassroomStudent::getStudentUserId)
                .toList();

        Map<UUID, User> usersMap = jpa.findAllById(userIds).stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toMap(User::getUserId, u -> u));

        List<ClassroomStudentData> studentDataList = new ArrayList<>();

        for (ClassroomStudent student : classroomStudents) {
            User user = usersMap.get(student.getStudentUserId());
            if (user != null) {
                studentDataList.add(ClassroomStudentData.from(
                        student,
                        user.getFirstName(),
                        user.getLastName(),
                        user.getProfileUrl()
                ));
            }
        }

        return studentDataList;
    }
}

