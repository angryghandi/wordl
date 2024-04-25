package com.angryghandi.wordl.web;

import com.angryghandi.wordl.dto.SearchRequest;
import com.angryghandi.wordl.service.WordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/available")
@RequiredArgsConstructor
public class WordController {

    private final WordService wordService;

    @GetMapping
    public ResponseEntity<List<String>> list() {
        return ResponseEntity.ok().body(wordService.list());
    }

    @PostMapping
    public ResponseEntity<List<String>> search(@Valid @RequestBody final SearchRequest searchRequest) {
        return ResponseEntity.ok().body(wordService.search(searchRequest));
    }
}
