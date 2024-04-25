package com.angryghandi.wordl.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static java.util.Objects.nonNull;

public class SearchWordValidator implements ConstraintValidator<SearchWordConstraint, String> {

    @Override
    public boolean isValid(final String word, final ConstraintValidatorContext constraintValidatorContext) {
        return nonNull(word) && word.matches("[A-Za-z_]{5}");
    }
    
}
