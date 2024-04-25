package com.angryghandi.wordl.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SearchLetterValidatorTest {

    private SearchLetterValidator cut;

    @BeforeEach
    void beforeEach() {
        cut = new SearchLetterValidator();
    }

    @ParameterizedTest
    @MethodSource("isValidParameters")
    void isValid(final List<Character> characters, final boolean expected) {
        assertThat(cut.isValid(characters, null)).isEqualTo(expected);
    }

    private static Stream<Arguments> isValidParameters() {
        return Stream.of(Arguments.of(null, true),
                Arguments.of(Collections.emptyList(), true),
                Arguments.of(List.of('a', 'b'), true),
                Arguments.of(List.of('a', 'B'), true),
                Arguments.of(List.of('a', '1'), false),
                Arguments.of(List.of('a', '/'), false)
        );
    }
}
