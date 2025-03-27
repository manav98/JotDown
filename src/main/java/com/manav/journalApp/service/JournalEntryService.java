package com.manav.journalApp.service;

import com.manav.journalApp.entity.JournalEntry;
import com.manav.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId journalId) {
        return journalEntryRepository.findById(journalId);
    }

    public JournalEntry saveEntry(JournalEntry journalEntry) {
        return journalEntryRepository.save(journalEntry);
    }

    public void deleteEntry(ObjectId journalId) {
        journalEntryRepository.deleteById(journalId);
    }

    public JournalEntry updateJournalById(ObjectId journalId, JournalEntry journalEntry) {
        JournalEntry existingEntry = journalEntryRepository.findById(journalId).orElse(null);
        if (existingEntry != null) {
            existingEntry.setTitle(journalEntry.getTitle() != null && !journalEntry.getTitle().isEmpty() ? journalEntry.getTitle() : existingEntry.getTitle());
            existingEntry.setContent(journalEntry.getContent() != null && !journalEntry.getContent().isEmpty() ? journalEntry.getContent() : existingEntry.getContent());

        }
        journalEntryRepository.save(existingEntry);
        return existingEntry;
    }

}
