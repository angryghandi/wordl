package com.angryghandi.wordl.service;

import com.angryghandi.wordl.dto.SearchRequest;
import com.angryghandi.wordl.dto.UsedWordRequest;
import com.angryghandi.wordl.dto.UsedWordResponse;

import java.util.List;

public interface WordService {

    String STATUS_SUCCESS = "ok";

    String STATUS_FAIL = "fail";

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

    /**
     * Update a word with used date.
     *
     * @param usedWordRequest {@link UsedWordRequest}
     * @return {@link UsedWordResponse} indicating if the update was successful or not
     */
    UsedWordResponse used(UsedWordRequest usedWordRequest);
}
