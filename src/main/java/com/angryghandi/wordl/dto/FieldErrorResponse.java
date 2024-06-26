package com.angryghandi.wordl.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder(toBuilder = true)
public class FieldErrorResponse {

    String field;

    String message;

}
