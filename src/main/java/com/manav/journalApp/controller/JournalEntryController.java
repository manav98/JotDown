package com.manav.journalApp.controller;

import com.manav.journalApp.entity.JournalEntry;
import com.manav.journalApp.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/_journal")
public class JournalEntryController {
    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll() {
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry journalEntry) {
        journalEntries.put(journalEntry.getId(), journalEntry);
        return true;
    }

    @GetMapping("/id/{journalId}")
    public JournalEntry getJournalEntryById(@PathVariable Long journalId) {
        return journalEntries.get(journalId);
    }

    @DeleteMapping("/id/{journalId}")
    public boolean deleteJournalById(@PathVariable Long journalId) {
        journalEntries.remove(journalId);
        return true;
    }

    @PutMapping("/id/{journalId}")
    public JournalEntry updateJournalById(@PathVariable Long journalId, @RequestBody JournalEntry journalEntry) {
        return journalEntries.put(journalId, journalEntry);
    }
}
