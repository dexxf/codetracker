package com.io.codetracker.application.activity.port.out;

import com.io.codetracker.domain.activity.entity.Activity;

public interface ActivityAppRepository {
    Activity save(Activity data);
}
