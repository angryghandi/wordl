package com.angryghandi.wordl.web.handler;

import com.angryghandi.wordl.dto.ErrorResponse;
import com.angryghandi.wordl.dto.FieldErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException exception, final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {

        final BindingResult bindingResult = exception.getBindingResult();
        final List<FieldErrorResponse> fieldErrors = new ArrayList<>();
        for (final FieldError fieldError : bindingResult.getFieldErrors()) {
            fieldErrors.add(FieldErrorResponse.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build());
        }

        ErrorResponse errorResponse = ErrorResponse.builder().fieldErrors(fieldErrors).build();
        return handleExceptionInternal(exception, errorResponse, headers, status, request);
    }
}
