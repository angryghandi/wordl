package com.angryghandi.wordl.validator;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SearchWordValidatorTest {

    private SearchWordValidator cut;

    @BeforeEach
    void beforeEach() {
        cut = new SearchWordValidator();
    }

    @ParameterizedTest
    @MethodSource("isValidParameters")
    void isValid(final String word, final boolean expected) {
        assertThat(cut.isValid(word, null)).isEqualTo(expected);
    }

    private static Stream<Arguments> isValidParameters() {
        return Stream.of(Arguments.of(null, false),
                Arguments.of(StringUtils.EMPTY, false),
                Arguments.of("and", false), // too short
                Arguments.of("around", false), // too long
                Arguments.of("a*ound", false), // illegal character
                Arguments.of("a%ound", false), // illegal character
                Arguments.of("about", true),
                Arguments.of("about", true),
                Arguments.of("a____", true),
                Arguments.of("a_o_t", true)

        );
    }
}
