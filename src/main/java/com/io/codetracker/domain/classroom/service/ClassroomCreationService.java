package com.io.codetracker.domain.classroom.service;

import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.classroom.entity.Classroom;
import com.io.codetracker.domain.classroom.entity.ClassroomSettings;
import com.io.codetracker.domain.classroom.factory.ClassroomFactory;
import com.io.codetracker.domain.classroom.repository.ClassroomUserDomainPort;
import com.io.codetracker.domain.classroom.result.ClassroomCreationEntity;
import com.io.codetracker.domain.classroom.result.ClassroomCreationResult;

public final class ClassroomCreationService {

    private static final int MAX_NAME_LENGTH = 100;
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_DESCRIPTION_LENGTH = 500;

    private final ClassroomFactory classroomFactory;
    private final ClassroomUserDomainPort classroomUserDomainPort;

    public ClassroomCreationService(ClassroomFactory classroomFactory, ClassroomUserDomainPort classroomUserDomainPort) {
        this.classroomFactory = classroomFactory;
        this.classroomUserDomainPort = classroomUserDomainPort;
    }

   
    public Result<ClassroomCreationEntity, ClassroomCreationResult> createClassroom(String instructorUserId,String name,String description,int maxStudents,Boolean requireApproval,
            String passcode) {

        if (instructorUserId == null || instructorUserId.isBlank()) {
            return Result.fail(ClassroomCreationResult.INVALID_INSTRUCTOR);
        }

        if (!classroomUserDomainPort.existsByUserId(instructorUserId)) {
            return Result.fail(ClassroomCreationResult.USERID_NOT_FOUND);
        }

        if (name == null || name.isBlank() || name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            return Result.fail(ClassroomCreationResult.INVALID_NAME);
        }

        if (description != null && description.length() > MAX_DESCRIPTION_LENGTH) {
            return Result.fail(ClassroomCreationResult.INVALID_DESCRIPTION);
        }

        if (maxStudents < ClassroomSettings.MIN_STUDENTS || maxStudents > ClassroomSettings.MAX_STUDENTS) {
            return Result.fail(ClassroomCreationResult.INVALID_MAX_STUDENTS);
        }

        Classroom classroom = classroomFactory.createClassroom(instructorUserId, name, description);

        if(classroom == null) {
            return Result.fail(ClassroomCreationResult.CLASSROOM_CREATION_FAILED);
        }

        ClassroomSettings settings = classroomFactory.createClassroomSetting(classroom.getClassroomId(), maxStudents, requireApproval, passcode);

        if (settings == null) {
            return Result.fail(ClassroomCreationResult.SETTING_CREATION_FAILED);
        }

        return Result.ok(new ClassroomCreationEntity(classroom, settings));
    }
}
