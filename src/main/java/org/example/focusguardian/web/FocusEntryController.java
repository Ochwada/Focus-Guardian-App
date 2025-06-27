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
 * * This controller handles all HTTP requests under the {@code /focus} path.
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
//    @GetMapping("/stats")
//    public Stats getStats() {
//        long total = repository.count();
//        long successful = 0;
//
//        for (FocusEntry entry : repository.findAll()) {
//            if (Boolean.TRUE.equals(entry.getStatus())) {
//                successful++;
//            }
//        }
//
//        double successRate = total > 0 ? (successful * 100.0) / total : 0.0;
//        return new Stats(total, successful, successRate);
//    }
    @GetMapping("/stats")
    public StatsResponse getStats() {
        List<FocusEntry> entries = new ArrayList<>();
        repository.findAll().forEach(entries::add);

        long total = entries.size();
        long successes = entries.stream().filter(FocusEntry::getStatus).count();
        long failures = total - successes;
        double successRate = total > 0 ? (successes * 100.0) / total : 0.0;

        return new StatsResponse(total, successes, failures, successRate);
    }

    // BONUS

    /**
     * Returns all successful focus entries where the status is {@code true}.
     * This endpoint is useful for viewing only the user's focus wins
     * (e.g., when Liam resisted using social media).
     *
     * @return a list of {@link FocusEntry} objects marked as successful
     */
    @GetMapping("/success")
    public Iterable<FocusEntry> getSuccessfulEntries() {
        List<FocusEntry> entries = new ArrayList<>();
        repository.findAll().forEach(entries::add);

        return entries.stream()
                .filter(e -> Boolean.TRUE.equals(e.getStatus()))
                .toList();
    }

    /**
     * Returns all failed focus entries where the status is {@code false}.
     * This endpoint is useful for tracking moments when the user gave in to distractions
     * — helpful for reflection and learning.
     *
     * @return a list of {@link FocusEntry} objects marked as failed
     */
    @GetMapping("/failure")
    public Iterable<FocusEntry> getFailedEntries() {
        List<FocusEntry> entries = new ArrayList<>();
        repository.findAll().forEach(entries::add);

        return entries.stream()
                .filter(e -> Boolean.FALSE.equals(e.getStatus()))
                .toList();
    }


    // Inner class for statistics response

    /**
     * A simple data transfer object (DTO) representing basic analytics
     * for user focus entries.
     * This class is used in the REST API response of
     * {@code GET /focus/stats} to return:
     * - the total number of entries,
     * - the number of successful entries,
     * - and the overall success rate.
     */
    static class Stats {
        /**
         * Total number of focus entries submitted by the user.
         */
        private final long totalEntries;

        /**
         * Number of focus entries where the user successfully avoided social media.
         */
        private final long successfulEntries;

        /**
         * Percentage of successful entries (0.0–100.0).
         */
        private final double successRate;

        /**
         * Constructs a new {@code Stats} object with all three analytics values.
         *
         * @param totalEntries      total number of entries submitted
         * @param successfulEntries number of successful (positive) entries
         * @param successRate       calculated success rate in percent
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

    //  Add Analytics Endpoint

    static class StatsResponse {
        private final long total;
        private final long successes;
        private final long failures;
        private final double successRate;

        public StatsResponse(long total, long successes, long failures, double successRate) {
            this.total = total;
            this.successes = successes;
            this.failures = failures;
            this.successRate = successRate;
        }

        public long getTotal() {
            return total;
        }

        public long getSuccesses() {
            return successes;
        }

        public long getFailures() {
            return failures;
        }

        public double getSuccessRate() {
            return successRate;
        }
    }


}

