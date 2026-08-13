package com.io.kira.adapter.user.out.cache;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component("userCacheKey")
public final class UserCacheKey {

    public String profileByUserId(UUID userId) {
        return "profile-by-user-id:" + userId;
    }
}
