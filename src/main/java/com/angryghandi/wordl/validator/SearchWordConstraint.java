package com.angryghandi.wordl.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = SearchWordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchWordConstraint {
    String message() default "Only five character strings of [A-Za-z_] are valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
