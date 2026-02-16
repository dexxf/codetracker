package com.io.codetracker.domain.classroom.repository;

public interface ClassroomUserDomainPort {
    boolean existsByUserId(String instructorUserId);
}