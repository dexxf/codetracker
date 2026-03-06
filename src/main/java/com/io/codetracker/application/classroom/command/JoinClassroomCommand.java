package com.io.codetracker.application.classroom.command;

public record JoinClassroomCommand(String userId, String code, String passcode) {
}