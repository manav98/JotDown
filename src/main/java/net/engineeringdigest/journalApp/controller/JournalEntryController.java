package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
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

    @GetMapping("/id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable Long myId) {
        return journalEntries.get(myId);
    }

    @DeleteMapping("/id/{journalId}")
    public boolean deleteEntryById(@PathVariable Long journalId) {
        journalEntries.remove(journalId);
        return true;
    }

    @PutMapping("/id/{journalId}")
    public JournalEntry updateJournalById(@PathVariable Long journalId, @RequestBody JournalEntry journalEntry) {
        return journalEntries.put(journalId, journalEntry);
    }
}
