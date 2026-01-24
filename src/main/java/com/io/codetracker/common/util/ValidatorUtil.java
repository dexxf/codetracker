package com.io.codetracker.common.util;


import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ValidatorUtil {

    private static final ValidatorFactory FACTORY = Validation.buildDefaultValidatorFactory();
    private static final Validator VALIDATOR = FACTORY.getValidator();

    private ValidatorUtil () {}

      public static Validator getValidator() {
       return VALIDATOR;
      }
}