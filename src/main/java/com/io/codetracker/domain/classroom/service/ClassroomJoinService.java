package com.io.codetracker.domain.classroom.service;

import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.classroom.entity.Classroom;
import com.io.codetracker.domain.classroom.entity.ClassroomSettings;
import com.io.codetracker.domain.classroom.repository.ClassroomDomainRepository;
import com.io.codetracker.domain.classroom.repository.ClassroomSettingsDomainRepository;
import com.io.codetracker.domain.classroom.repository.ClassroomStudentDomainRepository;
import com.io.codetracker.domain.classroom.result.ClassroomJoinValidationResult;
import com.io.codetracker.domain.classroom.valueObject.ClassroomStatus;

import java.util.Objects;
import java.util.Optional;

public class ClassroomJoinService {

    private final ClassroomDomainRepository classroomDomainRepository;
    private final ClassroomSettingsDomainRepository classroomSettingsDomainRepository;
    private final ClassroomStudentDomainRepository classroomStudentDomainRepository;

    public ClassroomJoinService(ClassroomDomainRepository classroomDomainRepository, ClassroomSettingsDomainRepository classroomSettingsDomainRepository,
                                ClassroomStudentDomainRepository classroomStudentDomainRepository) {
        this.classroomDomainRepository = classroomDomainRepository;
        this.classroomSettingsDomainRepository = classroomSettingsDomainRepository;
        this.classroomStudentDomainRepository = classroomStudentDomainRepository;
    }

    public Result<ClassroomJoinValidationResult, String> validate(String userId, String code, String passcode) {

        Optional<Classroom> classroomOptional = classroomDomainRepository.findByClassCode(code);

        if (classroomOptional.isEmpty()) return Result.fail("Code not found.");
        Classroom classroom = classroomOptional.get();

        if (classroom.getStatus() != ClassroomStatus.ACTIVE) {
            return Result.fail("Classroom is not active.");
        }

        if(classroom.getInstructorUserId().equals(userId)) {
            return Result.fail("You cannot join your own classroom.");
        }

        Optional<ClassroomSettings> classroomSettingsOptional = classroomSettingsDomainRepository.findByClassroomId(classroom.getClassroomId());

        if(classroomSettingsOptional.isEmpty()) return Result.fail("Error. Settings not found.");
        ClassroomSettings classroomSettings = classroomSettingsOptional.get();

        if (classroomSettings.getPasscode() == null && passcode != null && !passcode.isBlank()) {
            return Result.fail("Classroom does not require a passcode.");
        }

        if (classroomSettings.getPasscode() != null && !Objects.equals(classroomSettings.getPasscode(), passcode)) {
            return Result.fail("Invalid passcode.");
        }

        if (classroomStudentDomainRepository.existsByClassroomIdAndStudentUserId(classroom.getClassroomId(),userId)) {
            return Result.fail("User already in classroom.");
        }

        int currentStudentCount = classroomDomainRepository.countActiveStudentsByClassroomId(classroom.getClassroomId());
        if(currentStudentCount >= classroomSettings.getMaxStudents()) return Result.fail("Classroom is already full.");

        return Result.ok(new ClassroomJoinValidationResult(classroom, classroomSettings)
        );
    }


}
