package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll() {
        return journalEntryService.getAll();
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry journalEntry) {
        return journalEntryService.saveEntry(journalEntry);
    }

    @GetMapping("/id/{journalId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId journalId) {
        return journalEntryService.getJournalEntryById(journalId).orElse(null);
    }

    @DeleteMapping("/id/{journalId}")
    public boolean deleteEntryById(@PathVariable ObjectId journalId) {
        journalEntryService.deleteEntryById(journalId);
        return true;
    }

    @PutMapping("/id/{journalId}")
    public JournalEntry updateJournalById(@PathVariable ObjectId journalId, @RequestBody JournalEntry journalEntry) {
        JournalEntry oldEntry = journalEntryService.getJournalEntryById(journalId).orElse(null);
        if (oldEntry != null) {
            oldEntry.setContent(journalEntry.getContent() != null && !journalEntry.getContent().equalsIgnoreCase("") ? journalEntry.getContent() : oldEntry.getContent());
            oldEntry.setTitle(journalEntry.getTitle() != null && !journalEntry.getTitle().equalsIgnoreCase("") ? journalEntry.getTitle() : oldEntry.getTitle());
            journalEntryService.saveEntry(oldEntry);
        }
        return oldEntry;
    }
}
