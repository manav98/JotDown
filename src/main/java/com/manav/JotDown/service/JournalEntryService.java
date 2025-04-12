package com.manav.JotDown.service;

import com.manav.JotDown.entity.JournalEntry;
import com.manav.JotDown.entity.User;
import com.manav.JotDown.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public List<JournalEntry> getAllJournalEntriesOfUser(User user) {
        return user.getJournalEntries();
    }

    public Optional<JournalEntry> getEntryById(ObjectId journalId) {
        return journalEntryRepository.findById(journalId);
    }

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedJournalEntry = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(savedJournalEntry);
            userService.saveuser(user);
        } catch (Exception exception) {
            System.out.println(exception);
            throw new RuntimeException("An error occured while saving entry. ", exception);
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public void deleteEntry(ObjectId journalId, String userName) {
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(currentJournalEntry -> currentJournalEntry.getId().equals(journalId));
        userService.saveuser(user);
        journalEntryRepository.deleteById(journalId);
    }

    public JournalEntry updateJournalById(ObjectId journalId, JournalEntry journalEntry, String userName) {
        JournalEntry existingEntry = journalEntryRepository.findById(journalId).orElse(null);
        if (existingEntry != null) {
            existingEntry.setTitle(journalEntry.getTitle());
            existingEntry.setContent(journalEntry.getContent() != null && !journalEntry.getContent().isEmpty() ? journalEntry.getContent() : existingEntry.getContent());
            journalEntryRepository.save(existingEntry);
            return existingEntry;
        }
        return null;
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }
}
