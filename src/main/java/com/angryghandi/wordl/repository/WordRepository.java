package com.angryghandi.wordl.repository;

import com.angryghandi.wordl.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

    List<Word> findAllByOrderByWord();

    List<Word> findAllByUsedNullOrderByWord();

    List<Word> findAllByUsedNotNullOrderByWord();

    List<Word> findAllByWordLikeOrderByWord(final String word);
}
