package com.manav.journalApp.controller;

import com.manav.journalApp.entity.JournalEntry;
import com.manav.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<JournalEntry> getAll() {
        return journalEntryService.getAllEntries();
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        return journalEntryService.saveEntry(journalEntry);
    }

    @GetMapping("/id/{journalId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId journalId) {
        return journalEntryService.getEntryById(journalId).orElse(null);
    }

    @DeleteMapping("/id/{journalId}")
    public boolean deleteJournalById(@PathVariable ObjectId journalId) {
        journalEntryService.deleteEntry(journalId);
        return true;
    }

    @PutMapping("/id/{journalId}")
    public JournalEntry updateJournalById(@PathVariable ObjectId journalId, @RequestBody JournalEntry journalEntry) {
        return null;
    }
}
