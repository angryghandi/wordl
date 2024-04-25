package com.angryghandi.wordl.service.impl;

import com.angryghandi.wordl.dto.SearchRequest;
import com.angryghandi.wordl.entity.AvailableWord;
import com.angryghandi.wordl.repository.AvailableWordRepository;
import com.angryghandi.wordl.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

    private final AvailableWordRepository availableWordRepository;

    @Override
    public List<String> list() {
        final List<AvailableWord> words = availableWordRepository.findAll();
        return words.stream().map(AvailableWord::getWord).toList();
    }

    @Override
    public List<String> search(final SearchRequest searchRequest) {
        final String searchWord = searchRequest.getWord().toLowerCase();
        final List<AvailableWord> words = availableWordRepository.findAllByWordLikeOrderByWord(searchWord);

        return words.stream().map(AvailableWord::getWord)
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
