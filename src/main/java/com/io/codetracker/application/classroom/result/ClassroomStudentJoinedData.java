package com.io.codetracker.application.classroom.result;

import java.time.Instant;

public record ClassroomStudentJoinedData(
        String studentUserId,
        String firstName,
        String lastName,
        String profileUrl,
        Instant joinedAt
) {


}
