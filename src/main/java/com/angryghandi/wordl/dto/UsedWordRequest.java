package com.angryghandi.wordl.dto;

import com.angryghandi.wordl.validator.WordConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;

@Value
@Jacksonized
@Builder(toBuilder = true)
public class UsedWordRequest {

    @WordConstraint
    String word;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "US/Eastern")
    Date used;
}
