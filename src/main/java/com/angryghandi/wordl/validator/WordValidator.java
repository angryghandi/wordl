package com.angryghandi.wordl.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static java.util.Objects.nonNull;

public class WordValidator implements ConstraintValidator<WordConstraint, String> {

    @Override
    public boolean isValid(final String word, final ConstraintValidatorContext constraintValidatorContext) {
        return nonNull(word) && word.matches("[a-z]{5}");
    }

}
