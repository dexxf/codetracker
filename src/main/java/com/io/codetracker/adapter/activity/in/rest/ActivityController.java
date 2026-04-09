package com.io.codetracker.adapter.activity.in.rest;

import com.io.codetracker.adapter.activity.in.dto.request.SubmitExistingRepositoryRequest;
import com.io.codetracker.adapter.activity.in.dto.request.SubmitNewRepositoryRequest;
import com.io.codetracker.adapter.activity.in.dto.response.FindUnsubmittedRepositoryResponse;
import com.io.codetracker.adapter.activity.in.dto.response.GetActivityResponse;
import com.io.codetracker.adapter.activity.in.dto.response.GetSubmittedActivityResponse;
import com.io.codetracker.adapter.activity.in.dto.response.StudentActivityResponse;
import com.io.codetracker.adapter.activity.in.mapper.*;
import com.io.codetracker.adapter.auth.out.security.AuthPrincipal;
import com.io.codetracker.application.activity.command.AddActivityCommand;
import com.io.codetracker.application.activity.command.EditActivityCommand;
import com.io.codetracker.application.activity.command.FindUnsubmittedRepositoryCommand;
import com.io.codetracker.application.activity.command.GetActivityCommand;
import com.io.codetracker.adapter.activity.in.dto.request.AddActivityRequest;
import com.io.codetracker.adapter.activity.in.dto.request.EditActivityRequest;
import com.io.codetracker.adapter.activity.in.dto.response.ActivityResponse;
import com.io.codetracker.application.activity.error.*;
import com.io.codetracker.application.activity.port.in.*;
import com.io.codetracker.application.activity.result.ActivityData;

import com.io.codetracker.application.activity.result.StudentActivityData;
import com.io.codetracker.application.activity.result.SubmittedActivityUserData;
import com.io.codetracker.common.result.Result;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/classrooms/{classroomId}/activities")
@AllArgsConstructor
public class ActivityController {

    private final AddActivityUseCase addActivityUseCase;
    private final GetClassroomOwnerActivityUseCase getClassroomOwnerActivityUseCase;
    private final GetClassroomStudentActivityUseCase getClassroomStudentActivityUseCase;
    private final GetSubmittedActivityUseCase getSubmittedActivityUseCase;
    private final RemoveActivityUseCase removeActivityUseCase;
    private final EditActivityUseCase editActivityUseCase;
    private final SubmitExistingRepositoryUseCase submitExistingRepositoryUseCase;
    private final SubmitNewRepositoryUseCase submitNewRepositoryUseCase;
    private final FindStudentUnsubmittedRepositoryUseCase findStudentUnsubmittedRepositoryUseCase;

    @PostMapping
        public ResponseEntity<ActivityResponse> addActivity(@PathVariable String classroomId, @Valid @RequestBody AddActivityRequest request, @AuthenticationPrincipal AuthPrincipal principal) {
        AddActivityCommand command = new AddActivityCommand(classroomId, principal.getUserId(), request.title(),
                request.description(), request.dueDate(), request.maxScore(), request.status());
        Result<ActivityData, AddActivityError> response = addActivityUseCase.execute(command);
        return response.success() ? ResponseEntity.status(HttpStatus.CREATED).body(ActivityResponse.success(response.data(), "Successfully added activity"))
                                  : ResponseEntity.status(AddActivityHttpMapper.toStatus(response.error()))
                .body(ActivityResponse.fail(AddActivityHttpMapper.toMessage(response.error())));
    }

    @GetMapping("/owner")
    public ResponseEntity<GetActivityResponse> getClassroomOwnerActivities(@PathVariable String classroomId, @AuthenticationPrincipal AuthPrincipal principal) {
            Result<List<ActivityData>, GetClassroomOwnerActivityError> response =  getClassroomOwnerActivityUseCase.getOwnerClassroomActivity(new GetActivityCommand(classroomId,principal.getUserId()));
            return response.success() ? ResponseEntity.ok(GetActivityResponse.success(response.data()))
                                      : ResponseEntity.status(GetActivityHttpMapper.ownerToStatus(response.error()))
                    .body(GetActivityResponse.fail(GetActivityHttpMapper.ownerToMessage(response.error())));
    }

    @GetMapping("/student")
    public ResponseEntity<GetActivityResponse> getClassroomStudentActivities(@PathVariable String classroomId, @AuthenticationPrincipal AuthPrincipal principal) {
        Result<List<ActivityData>, GetClassroomStudentActivityError> response =  getClassroomStudentActivityUseCase.getStudentClassroomActivity(new GetActivityCommand(classroomId,principal.getUserId()));
        return response.success() ? ResponseEntity.ok(GetActivityResponse.success(response.data()))
                : ResponseEntity.status(GetActivityHttpMapper.studentToStatus(response.error()))
                  .body(GetActivityResponse.fail(GetActivityHttpMapper.studentToMessage(response.error())));
    }

    @GetMapping("/submitted")
    public ResponseEntity<GetSubmittedActivityResponse> getSubmittedActivities(@PathVariable String classroomId, @AuthenticationPrincipal AuthPrincipal principal) {
        Result<Map<String, SubmittedActivityUserData>, GetClassroomOwnerActivityError> response =
                getSubmittedActivityUseCase.execute(new GetActivityCommand(classroomId, principal.getUserId()));
        return response.success() ? ResponseEntity.ok(GetSubmittedActivityResponse.success(response.data()))
                : ResponseEntity.status(GetActivityHttpMapper.ownerToStatus(response.error()))
                .body(GetSubmittedActivityResponse.fail(GetActivityHttpMapper.ownerToMessage(response.error())));
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> removeActivity(@PathVariable String classroomId, @PathVariable String activityId, @AuthenticationPrincipal AuthPrincipal authPrincipal) {
        Result<ActivityData, RemoveActivityError> response = removeActivityUseCase.execute(classroomId,activityId,authPrincipal.getUserId());
        return !response.success() ?  ResponseEntity.status(RemoveActivityHttpMapper.toStatus(response.error()))
                .body(ActivityResponse.fail(RemoveActivityHttpMapper.toMessage(response.error())))
                : ResponseEntity.ok(ActivityResponse.success(response.data(), "Successfully Removed Activity"));
    }

    @PutMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> updateActivity(@PathVariable String classroomId, @PathVariable String activityId, @Valid @RequestBody EditActivityRequest request, @AuthenticationPrincipal AuthPrincipal authPrincipal) {
        Result<ActivityData, EditActivityError> response =  editActivityUseCase.execute(new EditActivityCommand(authPrincipal.getUserId(),
                classroomId,activityId,request.title(),request.description(),request.dueDate(),request.status(),request.maxScore()));

        return !response.success() ?  ResponseEntity.status(EditActivityHttpMapper.toStatus(response.error()))
                .body(ActivityResponse.fail(EditActivityHttpMapper.toMessage(response.error())))
                : ResponseEntity.ok(ActivityResponse.success(response.data(), "Successfully Updated Activity"));
        }


    @GetMapping("/unsubmitted")
    public ResponseEntity<FindUnsubmittedRepositoryResponse> getStudentUnsubmittedRepository(@AuthenticationPrincipal AuthPrincipal authPrincipal, @PathVariable String classroomId) {
        Result<List<ActivityData>, FindStudentUnsubmittedRepositoryError> result = findStudentUnsubmittedRepositoryUseCase.execute(new FindUnsubmittedRepositoryCommand(authPrincipal.getUserId(), classroomId));
        return result.success() ? ResponseEntity.ok(FindUnsubmittedRepositoryResponse.ok(result.data()))
                : ResponseEntity.status(FindUnsubmittedRepositoryHttpMapper.toStatus(result.error())).body(FindUnsubmittedRepositoryResponse.fail(FindUnsubmittedRepositoryHttpMapper.toMessage(result.error())));
    }

    @PostMapping("/{activityId}/submit/existing")
    public ResponseEntity<StudentActivityResponse> submitExistingRepository(
            @PathVariable String classroomId,
            @PathVariable String activityId,
            @Valid @RequestBody SubmitExistingRepositoryRequest request,
            @AuthenticationPrincipal AuthPrincipal authPrincipal
    ) {
        Result<StudentActivityData, SubmitExistingRepositoryError> response =
                submitExistingRepositoryUseCase.submitExisting(
                        authPrincipal.getUsername(),
                        authPrincipal.getUserId(),
                        classroomId,
                        activityId,
                        request.repositoryUrl()
                );

        return response.success()
                ? ResponseEntity.ok(StudentActivityResponse.success(response.data(), "Successfully submitted existing repository"))
                : ResponseEntity.status(SubmitExistingRepositoryHttpMapper.toStatus(response.error()))
                  .body(StudentActivityResponse.fail(SubmitExistingRepositoryHttpMapper.toMessage(response.error())));
    }

    @PostMapping("/{activityId}/submit/new")
    public ResponseEntity<StudentActivityResponse> submitNewRepository(
            @PathVariable String classroomId,
            @PathVariable String activityId,
            @Valid @RequestBody SubmitNewRepositoryRequest request,
            @AuthenticationPrincipal AuthPrincipal authPrincipal
    ) {
        Result<StudentActivityData, SubmitNewRepositoryError> response =
                submitNewRepositoryUseCase.submitNew(
                        authPrincipal.getUsername(),
                        authPrincipal.getUserId(),
                        classroomId,
                        activityId,
                        request.repositoryName()
                );

        return response.success()
                ? ResponseEntity.status(HttpStatus.CREATED)
                  .body(StudentActivityResponse.success(response.data(), "Successfully created and submitted repository"))
                : ResponseEntity.status(SubmitNewRepositoryHttpMapper.toStatus(response.error()))
                  .body(StudentActivityResponse.fail(SubmitNewRepositoryHttpMapper.toMessage(response.error())));
    }
}
