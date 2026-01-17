package com.io.codetracker.domain.user.valueobject;

import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.user.result.BirthdayResult;

import java.time.LocalDate;

public final class Birthday {

    private final LocalDate birthday;

    private Birthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public static Result<Birthday, BirthdayResult> of(LocalDate value) {

        if(value.isAfter(LocalDate.now())) {
            return Result.fail(BirthdayResult.BIRTHDAY_IN_FUTURE);
        }

        if(value.isAfter(LocalDate.now().minusYears(13))) {
            return Result.fail(BirthdayResult.TOO_YOUNG);
        }

        if(value.isBefore(LocalDate.now().minusYears(150))) {
            return Result.fail(BirthdayResult.TOO_OLD);
        }

        return Result.ok(new Birthday(value));
    }

    public LocalDate getValue() {
        return birthday;
    }

}
