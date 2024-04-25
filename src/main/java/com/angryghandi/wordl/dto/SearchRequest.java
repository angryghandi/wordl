package com.angryghandi.wordl.dto;

import com.angryghandi.wordl.validator.SearchLetterConstraint;
import com.angryghandi.wordl.validator.SearchWordConstraint;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;

@Value
@Jacksonized
@Builder(toBuilder = true)
public class SearchRequest {

    @SearchWordConstraint
    String word;

    @Builder.Default
    @SearchLetterConstraint
    List<Character> includes = new ArrayList<>();

    @Builder.Default
    @SearchLetterConstraint
    List<Character> excludes = new ArrayList<>();
}
