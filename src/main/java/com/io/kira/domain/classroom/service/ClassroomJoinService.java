package com.io.kira.domain.classroom.service;


import java.util.UUID;
import com.io.kira.common.result.Result;
import com.io.kira.domain.classroom.entity.Classroom;
import com.io.kira.domain.classroom.entity.ClassroomSettings;
import com.io.kira.domain.classroom.repository.ClassroomDomainRepository;
import com.io.kira.domain.classroom.repository.ClassroomSettingsDomainRepository;
import com.io.kira.domain.classroom.repository.ClassroomStudentDomainRepository;
import com.io.kira.domain.classroom.result.ClassroomJoinFailResult;
import com.io.kira.domain.classroom.result.ClassroomJoinValidationResult;
import com.io.kira.domain.classroom.valueObject.ClassroomStatus;

import java.util.Objects;
import java.util.Optional;

public class ClassroomJoinService {

    private final ClassroomDomainRepository classroomDomainRepository;
    private final ClassroomSettingsDomainRepository classroomSettingsDomainRepository;
    private final ClassroomStudentDomainRepository classroomStudentDomainRepository;

    public ClassroomJoinService(ClassroomDomainRepository classroomDomainRepository,
                                ClassroomSettingsDomainRepository classroomSettingsDomainRepository,
                                ClassroomStudentDomainRepository classroomStudentDomainRepository) {
        this.classroomDomainRepository = classroomDomainRepository;
        this.classroomSettingsDomainRepository = classroomSettingsDomainRepository;
        this.classroomStudentDomainRepository = classroomStudentDomainRepository;
    }

    public Result<ClassroomJoinValidationResult, ClassroomJoinFailResult> validate(UUID userId, String code, String passcode) {

        Optional<Classroom> classroomOptional = classroomDomainRepository.findByClassCode(code);
        if (classroomOptional.isEmpty()) return Result.fail(ClassroomJoinFailResult.CODE_NOT_FOUND);

        Classroom classroom = classroomOptional.get();

        if (classroom.getStatus() != ClassroomStatus.ACTIVE) {
            return Result.fail(ClassroomJoinFailResult.CLASSROOM_NOT_ACTIVE);
        }

        if(classroom.getInstructorUserId().equals(userId)) {
            return Result.fail(ClassroomJoinFailResult.CANNOT_JOIN_OWN_CLASSROOM);
        }

        Optional<ClassroomSettings> classroomSettingsOptional = classroomSettingsDomainRepository.findByClassroomId(classroom.getClassroomId());
        if(classroomSettingsOptional.isEmpty()) return Result.fail(ClassroomJoinFailResult.SETTINGS_NOT_FOUND);

        ClassroomSettings classroomSettings = classroomSettingsOptional.get();

        if (classroomSettings.getPasscode() != null) {
            if(passcode == null || passcode.isEmpty()) {
                return Result.fail(ClassroomJoinFailResult.PASSCODE_REQUIRED);
            }
            if(!Objects.equals(classroomSettings.getPasscode(), passcode)) {
                return Result.fail(ClassroomJoinFailResult.WRONG_PASSCODE);
            }
        }

        if (classroomStudentDomainRepository.existsByClassroomIdAndStudentUserId(classroom.getClassroomId(),userId)) {
            return Result.fail(ClassroomJoinFailResult.USER_ALREADY_IN_CLASSROOM);
        }

        int currentStudentCount = classroomStudentDomainRepository.countByClassroomId(classroom.getClassroomId());
        if(currentStudentCount >= classroomSettings.getMaxStudents()) {
            return Result.fail(ClassroomJoinFailResult.CLASSROOM_FULL);
        }

        return Result.ok(new ClassroomJoinValidationResult(classroom, classroomSettings)
        );
    }


}

