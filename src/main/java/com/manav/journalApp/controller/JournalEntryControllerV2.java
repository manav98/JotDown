package com.manav.journalApp.controller;

import com.manav.journalApp.entity.JournalEntry;
import com.manav.journalApp.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
    @Autowired
    private JournalRepository journalRepository;

    @GetMapping
    public List<JournalEntry> getAll() {
        return null;
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry journalEntry) {
        return true;
    }

    @GetMapping("/id/{journalId}")
    public JournalEntry getJournalEntryById(@PathVariable Long journalId) {
        return null;
    }

    @DeleteMapping("/id/{journalId}")
    public boolean deleteJournalById(@PathVariable Long journalId) {
        return true;
    }

    @PutMapping("/id/{journalId}")
    public JournalEntry updateJournalById(@PathVariable Long journalId, @RequestBody JournalEntry journalEntry) {
        return null; 
    }
}
