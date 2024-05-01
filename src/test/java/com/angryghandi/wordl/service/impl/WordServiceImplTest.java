package com.angryghandi.wordl.service.impl;

import com.angryghandi.wordl.dto.SearchRequest;
import com.angryghandi.wordl.dto.UsedWordRequest;
import com.angryghandi.wordl.dto.UsedWordResponse;
import com.angryghandi.wordl.entity.Word;
import com.angryghandi.wordl.repository.WordRepository;
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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static com.angryghandi.wordl.TestConstants.ABACK;
import static com.angryghandi.wordl.TestConstants.ABASE;
import static com.angryghandi.wordl.TestConstants.ABATE;
import static com.angryghandi.wordl.TestConstants.BACON;
import static com.angryghandi.wordl.TestConstants.CABAL;
import static com.angryghandi.wordl.TestConstants.WORDS;
import static com.angryghandi.wordl.service.WordService.STATUS_FAIL;
import static com.angryghandi.wordl.service.WordService.STATUS_SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class WordServiceImplTest {

    @Mock
    private WordRepository wordRepositoryMock;

    private Date used;

    private WordService cut;

    @BeforeEach
    void beforeEach() {
        cut = new WordServiceImpl(wordRepositoryMock);
        final Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.APRIL, 29);
        used = calendar.getTime();
    }

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(wordRepositoryMock);
    }

    @Test
    void all() {
        when(wordRepositoryMock.findAllByOrderByWord()).thenReturn(WORDS);

        final List<String> words = cut.all();

        assertThat(words).containsExactly(ABACK, BACON, CABAL);

        verify(wordRepositoryMock).findAllByOrderByWord();
    }

    @Test
    void used() {
        when(wordRepositoryMock.findAllByUsedNotNullOrderByWord()).thenReturn(WORDS);

        final List<String> words = cut.used();

        assertThat(words).containsExactly(ABACK, BACON, CABAL);

        verify(wordRepositoryMock).findAllByUsedNotNullOrderByWord();
    }

    @Test
    void unused() {
        when(wordRepositoryMock.findAllByUsedNullOrderByWord()).thenReturn(WORDS);

        final List<String> words = cut.unused();

        assertThat(words).containsExactly(ABACK, BACON, CABAL);

        verify(wordRepositoryMock).findAllByUsedNullOrderByWord();
    }

    @Test
    void search() {
        final String searchWord = "a_____";
        when(wordRepositoryMock.findAllByWordLikeOrderByWord(searchWord)).thenReturn(List.of(
                Word.builder().word(ABACK).build(),
                Word.builder().word(ABASE).build(),
                Word.builder().word(ABATE).build()
        ));

        final SearchRequest searchRequest = SearchRequest.builder()
                .word(searchWord)
                .includes(List.of('e'))
                .excludes(List.of('t'))
                .build();

        final List<String> words = cut.search(searchRequest);

        assertThat(words).containsExactly(ABASE);

        verify(wordRepositoryMock).findAllByWordLikeOrderByWord(searchWord);
    }

    @Test
    void used_not_found() {
        final UsedWordRequest usedWordRequest = UsedWordRequest.builder()
                .word(ABACK)
                .used(used)
                .build();
        when(wordRepositoryMock.findByWord(ABACK)).thenReturn(null);

        final UsedWordResponse usedWordResponse = cut.used(usedWordRequest);

        assertThat(usedWordResponse.getStatus()).isEqualTo(STATUS_FAIL);
        assertThat(usedWordResponse.getMessage()).isEqualTo("word is not in dictionary");
        assertThat(usedWordResponse.getWord()).isEqualTo(ABACK);
        assertThat(usedWordResponse.getUsed()).isEqualTo(used);

        verify(wordRepositoryMock).findByWord(ABACK);
    }

    @Test
    void used_already_used() {
        final UsedWordRequest usedWordRequest = UsedWordRequest.builder()
                .word(ABACK)
                .used(used)
                .build();
        when(wordRepositoryMock.findByWord(ABACK)).thenReturn(Word.builder()
                .word(ABACK)
                .used(used)
                .build());

        final UsedWordResponse usedWordResponse = cut.used(usedWordRequest);

        assertThat(usedWordResponse.getStatus()).isEqualTo(STATUS_FAIL);
        assertThat(usedWordResponse.getMessage()).isEqualTo("word already used on 2024-04-29");
        assertThat(usedWordResponse.getWord()).isEqualTo(ABACK);
        assertThat(usedWordResponse.getUsed()).isEqualTo(used);

        verify(wordRepositoryMock).findByWord(ABACK);
    }

    @Test
    void used_success() {
        final Word word = Word.builder()
                .word(ABACK)
                .build();
        final UsedWordRequest usedWordRequest = UsedWordRequest.builder()
                .word(ABACK)
                .used(used)
                .build();
        when(wordRepositoryMock.findByWord(ABACK)).thenReturn(word);

        final UsedWordResponse usedWordResponse = cut.used(usedWordRequest);

        assertThat(usedWordResponse.getStatus()).isEqualTo(STATUS_SUCCESS);
        assertThat(usedWordResponse.getMessage()).isNull();
        assertThat(usedWordResponse.getWord()).isEqualTo(ABACK);
        assertThat(usedWordResponse.getUsed()).isEqualTo(used);

        verify(wordRepositoryMock).save(word);
        verify(wordRepositoryMock).findByWord(ABACK);
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
                Arguments.of(ABATE, List.of('a', 'b', 'a', 't', 'x'), false),
                Arguments.of(ABATE, List.of('a', 'b', 'a', 'x', 'e'), false),
                Arguments.of(ABATE, List.of('a', 'b', 'x', 't', 'e'), false),
                Arguments.of(ABATE, List.of('a', 'x', 'a', 't', 'e'), false),
                Arguments.of(ABATE, List.of('x', 'b', 'a', 't', 'e'), false),
                Arguments.of(ABATE, List.of('a', 'b', 'a', 't', 'e'), true)
        );
    }

    static Stream<Arguments> excludesParameters() {
        return Stream.of(
                Arguments.of(ABATE, List.of('a', 'b', 'a', 't', 'x'), false),
                Arguments.of(ABATE, List.of('a', 'b', 'a', 'x', 'e'), false),
                Arguments.of(ABATE, List.of('a', 'b', 'x', 't', 'e'), false),
                Arguments.of(ABATE, List.of('a', 'x', 'a', 't', 'e'), false),
                Arguments.of(ABATE, List.of('x', 'b', 'a', 't', 'e'), false),
                Arguments.of(ABATE, List.of('s', 'p', 'u', 'r', 'n'), true)
        );
    }

}
