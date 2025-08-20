package com.guestbook_be.controller;

import com.guestbook_be.dto.*;
import com.guestbook_be.service.EntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/guestbook")
public class EntryController {
    private final EntryService service;

    @GetMapping
    public PageResponse<EntryResponse> list(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "20") int size
    ) {
        return service.list(cursor, Math.min(size, 50));
    }

    @PostMapping
    public EntryResponse create(@RequestBody @Valid EntryCreateRequest req) {
        return service.create(req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestParam String code) {
        service.delete(id, code);
        return ResponseEntity.ok().body("{\"success\":true}");
    }

    @PostMapping("/{id}/like")
    public EntryResponse like(@PathVariable Long id) {
        return service.like(id);
    }
}