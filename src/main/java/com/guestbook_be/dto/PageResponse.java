package com.guestbook_be.dto;

import java.util.List;

public record PageResponse<T>(List<T> items, Long nextCursor) {}
