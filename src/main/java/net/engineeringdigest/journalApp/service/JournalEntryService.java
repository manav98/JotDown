package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
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

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User currentUser = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedJournalEntry = journalEntryRepository.save(journalEntry);
            currentUser.getJournalEntries().add(savedJournalEntry);
            currentUser.setUserName(null);
            userService.saveUser(currentUser);
            return journalEntry;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new RuntimeException("An error occured while saving entry");
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public Optional<JournalEntry> getJournalEntryById(ObjectId journalId) {
        return journalEntryRepository.findById(journalId);
    }

    public void deleteEntryById(ObjectId journalId, String userName) {
        User currentUser = userService.findByUserName(userName);
        currentUser.getJournalEntries().removeIf(journalEntry -> journalEntry.getId().equals(journalId));
        userService.saveUser(currentUser);
        journalEntryRepository.deleteById(journalId);
    }
}
