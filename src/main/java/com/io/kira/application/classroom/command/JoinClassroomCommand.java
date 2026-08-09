package com.io.kira.application.classroom.command;


import java.util.UUID;
public record JoinClassroomCommand(UUID userId, String code, String passcode) {
}
