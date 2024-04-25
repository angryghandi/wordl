package com.angryghandi.wordl.service.impl;

import com.angryghandi.wordl.dto.SearchRequest;
import com.angryghandi.wordl.entity.AvailableWord;
import com.angryghandi.wordl.repository.AvailableWordRepository;
import com.angryghandi.wordl.service.WordService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class WordServiceImplTest {

    @Mock
    private AvailableWordRepository availableWordRepositoryMock;

    private WordService cut;

    @BeforeEach
    void beforeEach() {
        cut = new WordServiceImpl(availableWordRepositoryMock);
    }

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(availableWordRepositoryMock);
    }

    @Test
    void list() {
        when(availableWordRepositoryMock.findAll()).thenReturn(List.of(
                AvailableWord.builder().word("aback").build(),
                AvailableWord.builder().word("bacon").build(),
                AvailableWord.builder().word("cabal").build()
        ));


        final List<String> words = cut.list();

        assertThat(words).containsExactly("aback", "bacon", "cabal");

        verify(availableWordRepositoryMock).findAll();
    }

    @Test
    void search() {
        final String searchWord = "a_____";
        when(availableWordRepositoryMock.findAllByWordLikeOrderByWord(searchWord)).thenReturn(List.of(
                AvailableWord.builder().word("aback").build(),
                AvailableWord.builder().word("abase").build(),
                AvailableWord.builder().word("abate").build()
        ));

        final SearchRequest searchRequest = SearchRequest.builder()
                .word(searchWord)
                .includes(List.of('e'))
                .excludes(List.of('t'))
                .build();

        final List<String> words = cut.search(searchRequest);

        assertThat(words).containsExactly("abase");

        verify(availableWordRepositoryMock).findAllByWordLikeOrderByWord(searchWord);
    }

    @ParameterizedTest
    @MethodSource("includesParameters")
    void includes(final String word, final List<Character> characters, final boolean expected) {
        assertThat(((WordServiceImpl) cut).includes(word, characters)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("excludesParameters")
    void excludes(final String word, final List<Character> characters, final boolean expected) {
        assertThat(((WordServiceImpl) cut).excludes(word, characters)).isEqualTo(expected);
    }

    static Stream<Arguments> includesParameters() {
        return Stream.of(
                Arguments.of("about", List.of('a', 'b', 'o', 'u', 'x'), false),
                Arguments.of("about", List.of('a', 'b', 'o', 'x', 't'), false),
                Arguments.of("about", List.of('a', 'b', 'x', 'u', 't'), false),
                Arguments.of("about", List.of('a', 'x', 'o', 'y', 't'), false),
                Arguments.of("about", List.of('x', 'b', 'o', 'u', 't'), false),
                Arguments.of("about", List.of('a', 'b', 'o', 'u', 't'), true)
        );
    }

    static Stream<Arguments> excludesParameters() {
        return Stream.of(
                Arguments.of("about", List.of('a', 'b', 'o', 'u', 'x'), false),
                Arguments.of("about", List.of('a', 'b', 'o', 'x', 't'), false),
                Arguments.of("about", List.of('a', 'b', 'x', 'u', 't'), false),
                Arguments.of("about", List.of('a', 'x', 'o', 'y', 't'), false),
                Arguments.of("about", List.of('x', 'b', 'o', 'u', 't'), false),
                Arguments.of("about", List.of('s', 'p', 'e', 'n', 'd'), true)
        );
    }
}
