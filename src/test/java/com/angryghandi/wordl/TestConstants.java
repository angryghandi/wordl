package com.angryghandi.wordl;

import com.angryghandi.wordl.entity.Word;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class TestConstants {

    public static final String ABACK = "aback";

    public static final String ABASE = "abase";

    public static final String ABATE = "abate";

    public static final String BACON = "bacon";

    public static final String CABAL = "cabal";

    public static final List<Word> WORDS = List.of(
            Word.builder().word(ABACK).build(),
            Word.builder().word(BACON).build(),
            Word.builder().word(CABAL).build()
    );

    public static final List<String> WORD_STRINGS = WORDS.stream().map(Word::getWord).toList();
}
