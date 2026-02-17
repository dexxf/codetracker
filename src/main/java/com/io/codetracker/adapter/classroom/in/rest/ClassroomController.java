package com.io.codetracker.adapter.classroom.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.io.codetracker.adapter.auth.out.security.AuthPrincipal;
import com.io.codetracker.adapter.classroom.in.dto.CreateClassroomRequest;
import com.io.codetracker.application.classroom.command.CreateClassroomCommand;
import com.io.codetracker.application.classroom.response.CreateClassroomResponse;
import com.io.codetracker.application.classroom.service.CreateClassroomService;
import com.io.codetracker.application.classroom.service.GetClassroomsService;
import com.io.codetracker.common.response.ErrorResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController {
    
    private final CreateClassroomService createClassroomUseCase;
    private final GetClassroomsService getClassroomsUseCase;
    
    public ClassroomController(CreateClassroomService createClassroomUseCase, GetClassroomsService getClassroomsUseCase) {
        this.createClassroomUseCase = createClassroomUseCase;
        this.getClassroomsUseCase = getClassroomsUseCase;
    }
    
@PostMapping("/create")
public ResponseEntity<?> createClassroom(@AuthenticationPrincipal AuthPrincipal authPrincipal,@Valid @RequestBody CreateClassroomRequest request) {
    
    CreateClassroomResponse result = 
        createClassroomUseCase.execute(authPrincipal.getUserId(), new CreateClassroomCommand(
            request.name(),
            request.description(),
            request.maxStudents(),
            request.requireApproval(),
            request.passcode()
        ));
    
    if (!result.success()) {
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse.of(result.message(), 400));
    }
    
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(result.data());
}

    @GetMapping("/me")
    public ResponseEntity<?> getClassrooms(@AuthenticationPrincipal AuthPrincipal authPrincipal) {
        var result = getClassroomsUseCase.execute(authPrincipal.getUserId());
        return ResponseEntity.ok(result.data());
    }

}