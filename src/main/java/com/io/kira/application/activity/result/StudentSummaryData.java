package com.io.kira.application.activity.result;


import java.util.UUID;
public record StudentSummaryData(
        UUID userId,
        String firstName,
        String lastName,
        String profileUrl
) {
}

