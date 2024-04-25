package com.angryghandi.wordl.repository;

import com.angryghandi.wordl.entity.AvailableWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailableWordRepository extends JpaRepository<AvailableWord, Long> {

    List<AvailableWord> findAllByWordLikeOrderByWord(final String word);
}
