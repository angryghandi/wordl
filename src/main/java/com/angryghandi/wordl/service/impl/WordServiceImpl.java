package com.angryghandi.wordl.service.impl;

import com.angryghandi.wordl.dto.SearchRequest;
import com.angryghandi.wordl.dto.UsedWordRequest;
import com.angryghandi.wordl.dto.UsedWordResponse;
import com.angryghandi.wordl.entity.Word;
import com.angryghandi.wordl.repository.WordRepository;
import com.angryghandi.wordl.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;

    @Override
    public List<String> all() {
        final List<Word> words = wordRepository.findAllByOrderByWord();
        return words.stream().map(Word::getWord).toList();
    }

    @Override
    public List<String> used() {
        final List<Word> words = wordRepository.findAllByUsedNotNullOrderByWord();
        return words.stream().map(Word::getWord).toList();
    }

    @Override
    public List<String> unused() {
        final List<Word> words = wordRepository.findAllByUsedNullOrderByWord();
        return words.stream().map(Word::getWord).toList();
    }

    @Override
    public List<String> search(final SearchRequest searchRequest) {
        final String searchWord = searchRequest.getWord().toLowerCase();
        final List<Word> words = wordRepository.findAllByWordLikeOrderByWord(searchWord);

        return words.stream().map(Word::getWord)
                .filter(word -> includes(word, searchRequest.getIncludes()))
                .filter(word -> excludes(word, searchRequest.getExcludes()))
                .toList();
    }

    @Override
    public UsedWordResponse used(final UsedWordRequest usedWordRequest) {
        final Word word = wordRepository.findByWord(usedWordRequest.getWord());
        if (isNull(word)) {
            return UsedWordResponse.builder()
                    .status(STATUS_FAIL)
                    .message("word is not in dictionary")
                    .word(usedWordRequest.getWord())
                    .used(usedWordRequest.getUsed())
                    .build();
        }
        if (nonNull(word.getUsed())) {
            return UsedWordResponse.builder()
                    .status(STATUS_FAIL)
                    .message("word already used on " + new SimpleDateFormat("yyyy-MM-dd").format(word.getUsed()))
                    .word(usedWordRequest.getWord())
                    .used(usedWordRequest.getUsed())
                    .build();
        }

        word.setUsed(usedWordRequest.getUsed());
        wordRepository.save(word);

        return UsedWordResponse.builder()
                .status(STATUS_SUCCESS)
                .word(usedWordRequest.getWord())
                .used(usedWordRequest.getUsed())
                .build();
    }

    boolean includes(final String word, final List<Character> characters) {
        for (final Character character : characters) {
            if (word.indexOf(character) == -1) {
                return false;
            }
        }

        return true;
    }

    boolean excludes(final String word, final List<Character> characters) {
        for (final Character character : characters) {
            if (word.indexOf(character) != -1) {
                return false;
            }
        }
        return true;
    }
}
