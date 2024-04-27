package com.angryghandi.wordl.repository;


import com.angryghandi.wordl.entity.Word;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class WordRepositoryTest {

    @Autowired
    private WordRepository cut;

    @Test
    void findById() {
        final Long id = 1057L;
        final Optional<Word> optionalWord = cut.findById(id);

        assertThat(optionalWord).isPresent();
        final Word word = optionalWord.get();
        assertThat(word.getId()).isEqualTo(id);
        assertThat(word.getWord()).isEqualTo("joker");
        final Calendar cal = Calendar.getInstance();
        cal.set(2023, Calendar.APRIL, 25);
        assertThat(word.getUsed()).isInSameDayAs(cal.getTime());
    }

    @Test
    void findAllByOrderByWord() {
        final List<Word> words = cut.findAllByOrderByWord();

        assertThat(words).hasSize(2315);

        assertThat(words.get(0).getWord()).isEqualTo("aback");
        assertThat(words.get(1657).getWord()).isEqualTo("scale");
        assertThat(words.get(2314).getWord()).isEqualTo("zonal");
    }

    @Test
    void findAllByUsedNullOrderByWord() {
        final List<Word> words = cut.findAllByUsedNullOrderByWord();

        assertThat(words).hasSize(1271);
        assertThat(words.get(0).getWord()).isEqualTo("abbot");
        assertThat(words.get(636).getWord()).isEqualTo("macho");
        assertThat(words.get(1270).getWord()).isEqualTo("zonal");
    }

    @Test
    void findAllByUsedNotNullOrderByWord() {
        final List<Word> words = cut.findAllByUsedNotNullOrderByWord();

        assertThat(words).hasSize(1044);
        assertThat(words.get(0).getWord()).isEqualTo("aback");
        assertThat(words.get(522).getWord()).isEqualTo("limit");
        assertThat(words.get(1043).getWord()).isEqualTo("zesty");
    }
}
