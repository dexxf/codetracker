package com.io.codetracker.common.cache;

/**
 * Constants for application cache names.
 */
public final class CacheNames {

    // ORGANIZED BY MODULES

    // user
    public static final String USER_PROFILE = "userProfiles";

    // classroom
    public static final String CLASSROOM = "classroom";
    public static final String CLASSROOM_BY_ID = "classroomById";
    public static final String CLASSROOM_BY_CODE = "classroomByCode";
    public static final String CLASSROOM_SETTINGS = "classroomSettings";
    public static final String CLASSROOM_ACTIVITY_COUNTS = "classroomActivityCounts";

    private CacheNames() {}
}