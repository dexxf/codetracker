package com.io.codetracker.adapter.activity.in.rest;

import com.io.codetracker.adapter.auth.out.security.AuthPrincipal;
import com.io.codetracker.application.activity.command.AddActivityCommand;
import com.io.codetracker.application.activity.port.in.request.AddActivityRequest;
import com.io.codetracker.application.activity.port.in.response.AddActivityResponse;
import com.io.codetracker.application.activity.service.AddActivityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/classrooms/{classroomId}/activities")
@AllArgsConstructor
public class ActivityController {

    private final AddActivityService addActivityService;

    @PostMapping
    public ResponseEntity<?> addActivity(@PathVariable String classroomId, @RequestBody AddActivityRequest request, @AuthenticationPrincipal AuthPrincipal principal) {
        AddActivityCommand command = new AddActivityCommand(classroomId, principal.getUserId(), request.title(),
                request.description(), request.dueDate(), request.maxScore(), request.status());

        AddActivityResponse response = addActivityService.execute(command);

        if (!response.success())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}