package com.io.codetracker.domain.user.valueobject;

import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.user.result.PhoneNumberResult;

import java.util.regex.Pattern;

public final class PhoneNumber {
    private final String value;

    private PhoneNumber(String value) {
        this.value = value;
    }

    public static Result<PhoneNumber, PhoneNumberResult> of(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return Result.fail(PhoneNumberResult.PHONE_NUMBER_EMPTY);
        }

        if (!Pattern.compile("^(?:\\+63|0)9\\d{9}$").matcher(phoneNumber).matches()) {
            return Result.fail(PhoneNumberResult.INVALID_PHONE_NUMBER_FORMAT);
        }

        return Result.ok(new PhoneNumber(phoneNumber));
    }

    public String getValue() {
        return value;
    }
}
