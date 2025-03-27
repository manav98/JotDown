package com.manav.journalApp.repository;

import com.manav.journalApp.entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<Long, JournalEntry> {
}
