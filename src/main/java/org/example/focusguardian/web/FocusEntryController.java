package org.example.focusguardian.web;

import org.example.focusguardian.model.FocusEntry;
import org.example.focusguardian.repo.FocusEntryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * **********************************************************************+
 * Package Name          :org.example.focusguardian.web
 * Author                :ochwada
 * Name of the Project   :focus-guardian
 * Date                  :Friday,27. Jun.2025 at 16:13
 * Description           : REST controller for managing user focus entries in the Focus Guardian app.
 *  * This controller handles all HTTP requests under the {@code /focus} path.
 * Objective             :
 * /** ***********************************************************************+
 */


@RestController
@RequestMapping("/focus")
public class FocusEntryController {

    private final FocusEntryRepository repository;

    /**
     * Constructs the controller with a reference to the repository.
     *
     * @param repository the repository handling persistence
     */
    public FocusEntryController(FocusEntryRepository repository) {
        this.repository = repository;
    }

    // POST /focus — create a new entry
    /**
     * Submits a new focus entry to the system.
     *
     * @param entry the focus entry payload in JSON format
     * @return the saved {@link FocusEntry} with a generated ID and timestamp
     * </pre>
     */
    @PostMapping("/newEntry")
    public FocusEntry createEntry(@RequestBody FocusEntry entry) {
        return repository.save(entry);
    }

    // GET /focus/{id} — get one entry by ID
    /**
     * Retrieves a specific focus entry by its ID.
     *
     * @param id the ID of the entry
     * @return an {@link Optional} containing the entry if found
     */
    @GetMapping("/{id}")
    public Optional<FocusEntry> getEntry(@PathVariable Long id) {
        return repository.findById(id);
    }

    // GET /focus — list all entries
    /**
     * Returns all focus entries in the system.
     *
     * @return an {@link Iterable} list of all entries
     */
    @GetMapping("/entries")
    public Iterable<FocusEntry> getAllEntries() {
        return repository.findAll();
    }

    // GET /focus/stats — show basic stats
    /**
     * Calculates and returns basic statistics about the focus entries:
     * total count, successful entries, and success rate.
     *
     * @return a {@link Stats} object containing aggregated metrics
     */
    @GetMapping("/stats")
    public Stats getStats() {
        long total = repository.count();
        long successful = 0;

        for (FocusEntry entry : repository.findAll()) {
            if (Boolean.TRUE.equals(entry.getStatus())) {
                successful++;
            }
        }

        double successRate = total > 0 ? (successful * 100.0) / total : 0.0;
        return new Stats(total, successful, successRate);
    }

    // Inner class for statistics response
    /**
     * A simple data transfer object (DTO) representing basic analytics
     * for user focus entries.
     This class is used in the REST API response of
     * {@code GET /focus/stats} to return:
     * - the total number of entries,
     * - the number of successful entries,
     * - and the overall success rate.
     */
    static class Stats {
        /** Total number of focus entries submitted by the user. */
        private final long totalEntries;

        /** Number of focus entries where the user successfully avoided social media. */
        private final long successfulEntries;

        /** Percentage of successful entries (0.0–100.0). */
        private final double successRate;

        /**
         * Constructs a new {@code Stats} object with all three analytics values.
         *
         * @param totalEntries       total number of entries submitted
         * @param successfulEntries  number of successful (positive) entries
         * @param successRate        calculated success rate in percent
         */
        public Stats(long totalEntries, long successfulEntries, double successRate) {
            this.totalEntries = totalEntries;
            this.successfulEntries = successfulEntries;
            this.successRate = successRate;
        }

        public long getTotalEntries() {
            return totalEntries;
        }

        public long getSuccessfulEntries() {
            return successfulEntries;
        }

        public double getSuccessRate() {
            return successRate;
        }
    }
}

