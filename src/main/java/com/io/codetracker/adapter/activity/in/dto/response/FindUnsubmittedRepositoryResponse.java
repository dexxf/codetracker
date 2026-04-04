package com.io.codetracker.adapter.activity.in.dto.response;

import com.io.codetracker.application.activity.result.ActivityData;
import java.util.List;

public record FindUnsubmittedRepositoryResponse(
        List<ActivityData> data,
        String message
) {

    public static FindUnsubmittedRepositoryResponse ok(List<ActivityData> data) {
        return new FindUnsubmittedRepositoryResponse(data, "Success");
    }

    public static FindUnsubmittedRepositoryResponse fail(String message) {
        return new FindUnsubmittedRepositoryResponse(List.of(), message);
    }
}