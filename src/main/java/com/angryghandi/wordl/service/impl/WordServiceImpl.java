package com.angryghandi.wordl.service.impl;

import com.angryghandi.wordl.dto.SearchRequest;
import com.angryghandi.wordl.entity.Word;
import com.angryghandi.wordl.repository.WordRepository;
import com.angryghandi.wordl.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
