package com.guestbook_be.dto;

import java.time.LocalDateTime;

public record EntryResponse(
        Long id, String nickname, String message,
        LocalDateTime createdAt, Integer likeCount
) {}