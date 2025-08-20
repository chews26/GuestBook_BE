package com.guestbook_be.dto;

import jakarta.validation.constraints.*;

public record EntryCreateRequest(
        @NotBlank
        @Size(max=30)
        String nickname,

        @NotBlank
        @Size(max=1000)
        String message,

        @NotBlank
        @Size(min=3, max=64)
        String deleteCode
) {}