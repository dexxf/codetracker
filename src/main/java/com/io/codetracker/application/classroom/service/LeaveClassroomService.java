package com.io.codetracker.application.classroom.service;

import com.io.codetracker.application.classroom.command.LeaveClassroomCommand;
import com.io.codetracker.application.classroom.port.in.LeaveClassroomUseCase;
import com.io.codetracker.application.classroom.port.out.ClassroomAppRepository;
import com.io.codetracker.application.classroom.port.out.ClassroomStudentAppRepository;
import com.io.codetracker.application.classroom.result.LeaveClassroomResult;
import com.io.codetracker.domain.classroom.entity.ClassroomStudent;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LeaveClassroomService implements LeaveClassroomUseCase {

    private final ClassroomAppRepository classroomAppRepository;
    private final ClassroomStudentAppRepository classroomStudentAppRepository;

    @Override
    public LeaveClassroomResult execute(LeaveClassroomCommand command) {
        boolean classroomExists = classroomAppRepository.existsByClassroomId(command.classroomId());
        if (!classroomExists) {
            return LeaveClassroomResult.CLASSROOM_NOT_FOUND;
        }

        ClassroomStudent classroomStudent = classroomStudentAppRepository
                .findByClassroomIdAndStudentUserId(command.classroomId(), command.userId())
                .orElse(null);
        if (classroomStudent == null) {
            return LeaveClassroomResult.USER_NOT_IN_CLASSROOM;
        }
        if (classroomStudent.getStatus() != StudentStatus.ACTIVE) {
            return LeaveClassroomResult.USER_ALREADY_LEFT;
        }

        classroomStudent.leave();
        classroomStudentAppRepository.save(classroomStudent);
        return LeaveClassroomResult.SUCCESS;
    }
}
