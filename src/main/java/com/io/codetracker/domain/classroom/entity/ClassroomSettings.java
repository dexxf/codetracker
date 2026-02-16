package com.io.codetracker.domain.classroom.entity;

import com.io.codetracker.domain.classroom.exception.InvalidStudentQuantityException;

public final class ClassroomSettings {

    private final String classroomId;
    private String passcode;
    private int maxStudents;
    private boolean requireApproval;

    public static int MAX_STUDENTS = 100;
    public static int MIN_STUDENTS = 1;

    public ClassroomSettings(String classroomId,boolean requireApproval, String passcode, int maxStudents) {
        if (maxStudents < MIN_STUDENTS || maxStudents > MAX_STUDENTS) {
            throw new InvalidStudentQuantityException("Max students must be between " + MIN_STUDENTS + " and " + MAX_STUDENTS);
        }

        this.classroomId = classroomId;
        this.requireApproval = requireApproval;
        this.passcode = passcode;
        this.maxStudents = maxStudents;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public boolean isRequireApproval() {
        return requireApproval;
    }

    public String getPasscode() {
        return passcode;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void updateRequireApproval(boolean requireApproval) {
        this.requireApproval = requireApproval;
    }

    public void updatePasscode(String passcode) {
        this.passcode = passcode;
    }

    public void updateMaxStudents(int maxStudents) {
        if (maxStudents < MIN_STUDENTS || maxStudents > MAX_STUDENTS) {
            throw new InvalidStudentQuantityException("Max students must be between " + MIN_STUDENTS + " and " + MAX_STUDENTS);
        }
        this.maxStudents = maxStudents;
    }

    
}
