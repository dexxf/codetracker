package com.io.codetracker.adapter.activity.in.rest;

import com.io.codetracker.adapter.auth.out.security.AuthPrincipal;
import com.io.codetracker.application.activity.command.AddActivityCommand;
import com.io.codetracker.application.activity.command.GetActivityCommand;
import com.io.codetracker.application.activity.port.in.request.AddActivityRequest;
import com.io.codetracker.application.activity.port.in.response.AddActivityResponse;
import com.io.codetracker.application.activity.port.in.response.RemoveActivityResponse;
import com.io.codetracker.application.activity.service.AddActivityService;
import com.io.codetracker.application.activity.service.GetActivityService;
import com.io.codetracker.application.activity.service.RemoveActivityService;

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
    private final GetActivityService getActivityService;
    private final RemoveActivityService removeActivityService;

    @PostMapping
    public ResponseEntity<?> addActivity(@PathVariable String classroomId, @RequestBody AddActivityRequest request, @AuthenticationPrincipal AuthPrincipal principal) {
        AddActivityCommand command = new AddActivityCommand(classroomId, principal.getUserId(), request.title(),
                request.description(), request.dueDate(), request.maxScore(), request.status());

        AddActivityResponse response = addActivityService.execute(command);

        return response.success() ? ResponseEntity.status(HttpStatus.CREATED).body(response)
                                  : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getActivities(@PathVariable String classroomId, @AuthenticationPrincipal AuthPrincipal principal) {
            var response =  getActivityService.execute(new GetActivityCommand(classroomId,principal.getUserId()));

            return response.success() ? ResponseEntity.ok().body(response)
                                      : ResponseEntity.badRequest().body(response);
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<?> removeActivity(@PathVariable String classroomId, @PathVariable String activityId, @AuthenticationPrincipal AuthPrincipal authPrincipal) {
        RemoveActivityResponse response = removeActivityService.execute(classroomId,activityId,authPrincipal.getUserId());
        return !response.success() ?  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.message())
        : ResponseEntity.ok(response);
    }
}