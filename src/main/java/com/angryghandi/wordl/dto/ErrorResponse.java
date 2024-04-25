package com.angryghandi.wordl.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Jacksonized
@Builder(toBuilder = true)
public class ErrorResponse {

    String message;

    List<FieldErrorResponse> fieldErrors;
    
}
