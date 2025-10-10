package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntry> userJournalEntries = user.getJournalEntries();
        if (userJournalEntries.isEmpty()) {
            return new ResponseEntity<>(userJournalEntries, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userJournalEntries, HttpStatus.OK);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry, @PathVariable String userName) {
        try {
            return new ResponseEntity<>(journalEntryService.saveEntry(journalEntry, userName), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{journalId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId journalId) {
        Optional<JournalEntry> journalEntryById = journalEntryService.getJournalEntryById(journalId);
        if (journalEntryById.isPresent()) {
            return new ResponseEntity<>(journalEntryById.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{userName}/{journalId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId journalId, @PathVariable String userName) {
        journalEntryService.deleteEntryById(journalId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{userName}/{journalId}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId journalId, @RequestBody JournalEntry journalEntry, @PathVariable String userName) {
        JournalEntry oldEntry = journalEntryService.getJournalEntryById(journalId).orElse(null);
        if (oldEntry != null) {
            oldEntry.setContent(journalEntry.getContent() != null && !journalEntry.getContent().equalsIgnoreCase("") ? journalEntry.getContent() : oldEntry.getContent());
            oldEntry.setTitle(journalEntry.getTitle() != null && !journalEntry.getTitle().equalsIgnoreCase("") ? journalEntry.getTitle() : oldEntry.getTitle());
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
