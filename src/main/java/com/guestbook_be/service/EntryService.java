package com.guestbook_be.service;

import com.guestbook_be.entity.Entry;
import com.guestbook_be.dto.*;
import com.guestbook_be.repository.EntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EntryService {

    private final EntryRepository repo;
    private final PasswordEncoder encoder;

    @Transactional
    public EntryResponse create(EntryCreateRequest req) {
        Entry e = Entry.builder()
                .nickname(req.nickname().trim())
                .message(req.message().trim())
                .deleteCodeHash(encoder.encode(req.deleteCode()))
                .build();
        Entry saved = repo.save(e);
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public PageResponse<EntryResponse> list(Long cursor, int size) {
        List<Entry> data = cursor == null
                ? repo.findAllByOrderByIdDesc(PageRequest.of(0, size))
                : repo.findByIdLessThanOrderByIdDesc(cursor, PageRequest.of(0, size));

        Long next = (data.size() == size && !data.isEmpty())
                ? data.get(data.size() - 1).getId()
                : null;

        return new PageResponse<>(data.stream().map(this::toDto).toList(), next);
    }

    @Transactional
    public void delete(Long id, String code) {
        Entry e = repo.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        if (!encoder.matches(code, e.getDeleteCodeHash()))
            throw new ResponseStatusException(FORBIDDEN, "INVALID_DELETE_CODE");
        repo.delete(e);
    }

    @Transactional
    public EntryResponse like(Long id) {
        Entry e = repo.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        e.setLikeCount(e.getLikeCount() + 1);
        return toDto(e);
    }

    private EntryResponse toDto(Entry e) {
        return new EntryResponse(e.getId(), e.getNickname(), e.getMessage(), e.getCreatedAt(), e.getLikeCount());
    }
}
