package com.io.kira.application.classroom.result;


import java.util.UUID;
import java.time.Instant;

public record ClassroomStudentJoinedData(
        UUID studentUserId,
        String firstName,
        String lastName,
        String profileUrl,
        Instant joinedAt
) {


}

