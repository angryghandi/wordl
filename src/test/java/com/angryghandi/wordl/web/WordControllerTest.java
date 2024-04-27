package com.angryghandi.wordl.web;

import com.angryghandi.wordl.dto.SearchRequest;
import com.angryghandi.wordl.service.WordService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class WordControllerTest {

    @Mock
    private WordService wordServiceMock;

    private WordController cut;

    @BeforeEach
    void beforeEach() {
        cut = new WordController(wordServiceMock);
    }

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(wordServiceMock);
    }

    @Test
    void all() {
        when(wordServiceMock.all()).thenReturn(List.of("aback", "bacon", "cabal"));

        final ResponseEntity<List<String>> response = cut.all();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactly("aback", "bacon", "cabal");

        verify(wordServiceMock).all();
    }

    @Test
    void used() {
        when(wordServiceMock.used()).thenReturn(List.of("aback", "bacon", "cabal"));

        final ResponseEntity<List<String>> response = cut.used();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactly("aback", "bacon", "cabal");

        verify(wordServiceMock).used();
    }

    @Test
    void unused() {
        when(wordServiceMock.unused()).thenReturn(List.of("aback", "bacon", "cabal"));

        final ResponseEntity<List<String>> response = cut.unused();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactly("aback", "bacon", "cabal");

        verify(wordServiceMock).unused();
    }

    @Test
    void search() {
        final SearchRequest searchRequest = SearchRequest.builder().build();
        when(wordServiceMock.search(searchRequest)).thenReturn(List.of("aback", "bacon", "cabal"));

        final ResponseEntity<List<String>> response = cut.search(searchRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactly("aback", "bacon", "cabal");

        verify(wordServiceMock).search(searchRequest);
    }
}
