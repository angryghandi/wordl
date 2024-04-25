package com.angryghandi.wordl.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

import static java.util.Objects.nonNull;

public class SearchLetterValidator implements ConstraintValidator<SearchLetterConstraint, List<Character>> {

    @Override
    public boolean isValid(final List<Character> characters, final ConstraintValidatorContext constraintValidatorContext) {
        if (nonNull(characters) && !characters.isEmpty()) {
            for (final Character character : characters) {
                if (!Character.isLetter(character)) {
                    return false;
                }
            }
        }
        return true;
    }

}
