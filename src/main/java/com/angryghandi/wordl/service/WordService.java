package com.angryghandi.wordl.service;

import com.angryghandi.wordl.dto.SearchRequest;

import java.util.List;

public interface WordService {

    /**
     * Get list of all words.
     *
     * @return list of all words
     */
    List<String> all();

    /**
     * Get list of all used words.
     *
     * @return list of used words
     */
    List<String> used();

    /**
     * Get list of unused words.
     *
     * @return list of unused words
     */
    List<String> unused();

    /**
     * Search for possible words given the {@link SearchRequest}.
     *
     * @param searchRequest {@link SearchRequest}
     * @return list of words matching criteria in {@link SearchRequest}
     */
    List<String> search(final SearchRequest searchRequest);
}
