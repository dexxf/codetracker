package com.io.kira.adapter.activity.in.dto.response;

import com.io.kira.application.activity.result.ActivityDetailsData;
import java.util.List;

public record FindUnsubmittedRepositoryResponse(
        List<ActivityDetailsData> data,
        String message
) {

    public static FindUnsubmittedRepositoryResponse ok(List<ActivityDetailsData> data) {
        return new FindUnsubmittedRepositoryResponse(data, "Success");
    }

    public static FindUnsubmittedRepositoryResponse fail(String message) {
        return new FindUnsubmittedRepositoryResponse(List.of(), message);
    }
}