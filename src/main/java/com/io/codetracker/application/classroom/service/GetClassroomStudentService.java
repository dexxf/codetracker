package com.io.codetracker.application.classroom.service;

import com.io.codetracker.application.classroom.command.GetClassroomStudentCommand;
import com.io.codetracker.application.classroom.error.SimpleClassroomError;
import com.io.codetracker.application.classroom.port.in.GetClassroomStudentUseCase;
import com.io.codetracker.application.classroom.port.out.ClassroomAppRepository;
import com.io.codetracker.application.classroom.port.out.ClassroomStudentAppRepository;
import com.io.codetracker.application.classroom.port.out.ClassroomStudentUserAppPort;
import com.io.codetracker.application.classroom.result.ClassroomStudentData;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.classroom.entity.ClassroomStudent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetClassroomStudentService implements GetClassroomStudentUseCase {

    private final ClassroomStudentAppRepository classroomStudentAppRepository;
    private final ClassroomAppRepository classroomAppRepository;
    private final ClassroomStudentUserAppPort classroomStudentUserAppPort;

    public Result<List<ClassroomStudentData>, SimpleClassroomError> execute(GetClassroomStudentCommand command) {
        if (!classroomAppRepository.existsByClassroomIdAndInstructorUserId(command.classroomId(), command.userId())){
            return Result.fail(SimpleClassroomError.USER_NOT_CLASSROOM_INSTRUCTOR);
        }

        List<ClassroomStudent> classroomStudents = classroomStudentAppRepository.findClassroomStudents(
                command.classroomId(),
                command.status(),
                command.ascending()
        );

        List<ClassroomStudentData> studentDataList = classroomStudentUserAppPort.addUserData(classroomStudents);
        return Result.ok(studentDataList);
    }

}