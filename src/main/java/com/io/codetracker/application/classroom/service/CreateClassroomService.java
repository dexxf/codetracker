package com.io.codetracker.application.classroom.service;


import org.springframework.stereotype.Service;
import com.io.codetracker.application.classroom.command.CreateClassroomCommand;
import com.io.codetracker.application.classroom.port.out.ClassroomAppRepository;
import com.io.codetracker.application.classroom.port.in.response.CreateClassroomResponse;
import com.io.codetracker.application.classroom.result.CreateClassroomData;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.classroom.result.ClassroomCreationEntity;
import com.io.codetracker.domain.classroom.result.ClassroomCreationResult;
import com.io.codetracker.domain.classroom.service.ClassroomCreationService;

@Service
public class CreateClassroomService {
    
    private final ClassroomCreationService classroomCreationService;
    private final ClassroomAppRepository classroomAppRepository;
    
    public CreateClassroomService(
        ClassroomCreationService classroomCreationService,
        ClassroomAppRepository classroomAppRepository
    ) {
        this.classroomCreationService = classroomCreationService;
        this.classroomAppRepository = classroomAppRepository;
    }
    
    public CreateClassroomResponse execute(String userId, CreateClassroomCommand command) {

        Result<ClassroomCreationEntity, ClassroomCreationResult> result =
            classroomCreationService.createClassroom(
                userId,
                command.name(),
                command.description(),
                command.maxStudents(),
                command.requireApproval(),
                command.passcode()
            );
        
        if (!result.success()) {
            return CreateClassroomResponse.fail(result.error().getMessage());
        }
        
        ClassroomCreationEntity creationEntity = result.data();
        classroomAppRepository.saveClassroom(creationEntity.classroom(), creationEntity.settings());
        
     CreateClassroomData response = new CreateClassroomData(
        creationEntity.classroom().getClassroomId(),
        creationEntity.classroom().getName(),
        creationEntity.classroom().getDescription(),
        creationEntity.classroom().getClassCode(),
        creationEntity.classroom().getStatus().name(),
        creationEntity.settings().getMaxStudents(),
        creationEntity.settings().isRequireApproval()
    );

        return CreateClassroomResponse.ok(response);
    }

 
}