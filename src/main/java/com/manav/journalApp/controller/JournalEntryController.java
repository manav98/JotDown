package com.manav.journalApp.controller;

import com.manav.journalApp.entity.JournalEntry;
import com.manav.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll() {
        return new ResponseEntity<>(journalEntryService.getAllEntries(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
        try {
            journalEntry.setDate(LocalDateTime.now());
            return new ResponseEntity<>(journalEntryService.saveEntry(journalEntry), HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{journalId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId journalId) {
        Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(journalId);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/id/{journalId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId journalId) {
        journalEntryService.deleteEntry(journalId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/id/{journalId}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId journalId, @RequestBody JournalEntry journalEntry) {
        return new ResponseEntity<>(journalEntryService.updateJournalById(journalId, journalEntry), HttpStatus.OK);
    }
}
