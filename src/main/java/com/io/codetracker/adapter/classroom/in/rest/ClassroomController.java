package com.io.codetracker.adapter.classroom.in.rest;

import com.io.codetracker.adapter.classroom.in.dto.JoinClassroomRequest;
import com.io.codetracker.application.classroom.command.JoinClassroomCommand;
import com.io.codetracker.application.classroom.service.GetJoinClassroomService;
import com.io.codetracker.application.classroom.service.JoinClassroomService;
import lombok.AllArgsConstructor;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/classrooms")
@AllArgsConstructor
public class ClassroomController {
    
    private final CreateClassroomService createClassroomUseCase;
    private final GetClassroomsService getClassroomsUseCase;
    private final JoinClassroomService joinClassroomService;
    private final GetJoinClassroomService getJoinClassroomService;
    
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
        return ResponseEntity.ok(Optional.ofNullable(result.data()).orElse(List.of()));
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinClassroom(@AuthenticationPrincipal AuthPrincipal authPrincipal, @Valid @RequestBody JoinClassroomRequest request) {
        var response = joinClassroomService.execute(
                new JoinClassroomCommand(authPrincipal.getUserId(), request.code(), request.passcode()));

        if (!response.success()) {
            return ResponseEntity.badRequest().body(ErrorResponse.of(response.error(), 400));
        }

        return ResponseEntity.ok(response.data());
    }

    @GetMapping("/join")
    public ResponseEntity<?> getJoinedClassrooms(
            @AuthenticationPrincipal AuthPrincipal authPrincipal) {
        var result = getJoinClassroomService.execute(authPrincipal.getUserId());
        return ResponseEntity.ok(result);
    }

}