package com.manav.JotDown.controller;

import com.manav.JotDown.entity.JournalEntry;
import com.manav.JotDown.entity.User;
import com.manav.JotDown.service.JournalEntryService;
import com.manav.JotDown.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllEntries() {
        return new ResponseEntity<>(journalEntryService.getAllEntries(), HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntry> allEntries = journalEntryService.getAllJournalEntriesOfUser(user);
        if (allEntries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allEntries, HttpStatus.OK);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry, @PathVariable String userName) {
        try {
            journalEntryService.saveEntry(journalEntry, userName);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/id/{journalId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId journalId) {
        Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(journalId);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/id/{userName}/{journalId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId journalId, @PathVariable String userName) {
        journalEntryService.deleteEntry(journalId, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/id/{userName}/{journalId}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId journalId, @RequestBody JournalEntry journalEntry, @PathVariable String userName) {
        return new ResponseEntity<>(journalEntryService.updateJournalById(journalId, journalEntry, userName), HttpStatus.OK);
    }
}
