package com.io.codetracker.application.classroom.service;

import com.io.codetracker.application.classroom.command.GetClassroomStudentCommand;
import com.io.codetracker.application.classroom.port.in.response.GetClassroomStudentResponse;
import com.io.codetracker.application.classroom.port.out.ClassroomAppRepository;
import com.io.codetracker.application.classroom.port.out.ClassroomStudentAppRepository;
import com.io.codetracker.application.classroom.port.out.ClassroomStudentUserAppPort;
import com.io.codetracker.application.classroom.result.ClassroomStudentData;
import com.io.codetracker.domain.classroom.entity.ClassroomStudent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetClassroomStudentService {

    private final ClassroomStudentAppRepository classroomStudentAppRepository;
    private final ClassroomAppRepository classroomAppRepository;
    private final ClassroomStudentUserAppPort classroomStudentUserAppPort;

    public GetClassroomStudentResponse execute(GetClassroomStudentCommand command) {
        if (!classroomAppRepository.existsByClassroomIdAndInstructorUserId(command.classroomId(), command.userId())){
            return GetClassroomStudentResponse.fail("You are not the owner of this classroom.");
        }

        List<ClassroomStudent> classroomStudents = classroomStudentAppRepository.findClassroomStudents(
                command.classroomId(),
                command.status(),
                command.ascending()
        );

        List<ClassroomStudentData> studentDataList = classroomStudentUserAppPort.addUserData(classroomStudents);
        return GetClassroomStudentResponse.ok(studentDataList);
    }

}