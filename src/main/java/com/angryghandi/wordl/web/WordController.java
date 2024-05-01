package com.angryghandi.wordl.web;

import com.angryghandi.wordl.dto.SearchRequest;
import com.angryghandi.wordl.dto.UsedWordRequest;
import com.angryghandi.wordl.dto.UsedWordResponse;
import com.angryghandi.wordl.service.WordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WordController {

    private final WordService wordService;

    @GetMapping
    public ResponseEntity<List<String>> all() {
        return ResponseEntity.ok().body(wordService.all());
    }

    @GetMapping("/used")
    public ResponseEntity<List<String>> used() {
        return ResponseEntity.ok().body(wordService.used());
    }

    @GetMapping("/unused")
    public ResponseEntity<List<String>> unused() {
        return ResponseEntity.ok().body(wordService.unused());
    }

    @PostMapping("/search")
    public ResponseEntity<List<String>> search(@Valid @RequestBody final SearchRequest searchRequest) {
        return ResponseEntity.ok().body(wordService.search(searchRequest));
    }

    @PostMapping("/used")
    public ResponseEntity<UsedWordResponse> used(@Valid @RequestBody final UsedWordRequest usedWordRequest) {
        return ResponseEntity.ok().body(wordService.used(usedWordRequest));
    }
}
