package com.angryghandi.wordl.service;

import com.angryghandi.wordl.dto.SearchRequest;

import java.util.List;

public interface WordService {

    /**
     * Get a list of all possible wordl words.
     *
     * @return list of possible wordl words
     */
    List<String> list();

    /**
     * Search for possible words given the {@link SearchRequest}.
     *
     * @param searchRequest {@link SearchRequest}
     * @return list of words matching criteria in {@link SearchRequest}
     */
    List<String> search(final SearchRequest searchRequest);
}
