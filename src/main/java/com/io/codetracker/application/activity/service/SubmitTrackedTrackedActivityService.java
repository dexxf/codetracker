package com.io.codetracker.application.activity.service;

import com.io.codetracker.application.activity.error.SubmitActivityError;
import com.io.codetracker.application.activity.port.in.SubmitTrackedActivityUseCase;
import com.io.codetracker.application.activity.port.out.ActivityClassroomAppPort;
import com.io.codetracker.application.activity.port.out.StudentActivityAppRepository;
import com.io.codetracker.application.activity.result.StudentActivitySubmissionData;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.activity.entity.StudentActivity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SubmitTrackedTrackedActivityService implements SubmitTrackedActivityUseCase {

    private final StudentActivityAppRepository studentActivityAppRepository;
    private final ActivityClassroomAppPort activityClassroomAppPort;

    @Override
    public Result<StudentActivitySubmissionData, SubmitActivityError> submit(UUID userId, UUID classroomId, String activityId) {
        if (!activityClassroomAppPort.existsByClassroomId(classroomId))
            return Result.fail(SubmitActivityError.CLASSROOM_NOT_FOUND);

        if (!studentActivityAppRepository.existsByUserId(userId))
            return Result.fail(SubmitActivityError.USER_NOT_FOUND);

        if (!activityClassroomAppPort.existsByClassroomIdAndActivityId(classroomId, activityId))
            return Result.fail(SubmitActivityError.ACTIVITY_NOT_FOUND);

        if (!activityClassroomAppPort.existsByClassroomIdAndStudentUserId(classroomId, userId))
            return Result.fail(SubmitActivityError.USER_NOT_CLASSROOM_STUDENT);

        Optional<StudentActivity> studentActivityOptional = studentActivityAppRepository.findByUserIdAndActivityId(userId, activityId);
        if (studentActivityOptional.isEmpty())
            return Result.fail(SubmitActivityError.REPOSITORY_SUBMISSION_NOT_FOUND);

        StudentActivity studentActivity = studentActivityOptional.get();

        try {
            studentActivity.submit();
        } catch (IllegalStateException e) {
            return Result.fail(SubmitActivityError.ALREADY_SUBMITTED);
        }

        try {
            StudentActivity savedStudentActivity = studentActivityAppRepository.save(studentActivity);
            return Result.ok(StudentActivitySubmissionData.from(savedStudentActivity));
        } catch (RuntimeException e) {
            return Result.fail(SubmitActivityError.SAVE_FAILED);
        }
    }
}
