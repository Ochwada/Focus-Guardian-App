package org.example.focusguardian.repo;

import org.example.focusguardian.model.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * **********************************************************************+
 * Package Name          :org.example.focusguardian.repo
 * Author                :ochwada
 * Name of the Project   :focus-guardian
 * Date                  :Friday,27. Jun.2025 at 16:11
 * Description           :
 * Objective             :
 * /** ***********************************************************************+
 */
@Repository
public interface FocusEntryRepository extends CrudRepository<FocusEntry, Long> {
    // No code needed — Spring will auto-implement standard methods
    /**
     * save(FocusEntry entry)
     * findAll()
     * findById(Long id)
     * deleteById(Long id)
     * ...and more from CrudRepository
     */
}