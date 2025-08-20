package com.guestbook_be.repository;

import com.guestbook_be.entity.Entry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> findAllByOrderByIdDesc(Pageable pageable);
    List<Entry> findByIdLessThanOrderByIdDesc(Long id, Pageable pageable);
}