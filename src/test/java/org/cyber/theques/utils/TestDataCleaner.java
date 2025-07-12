package org.cyber.theques.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.cyber.theques.adapter.persistence.repository.BookPanacheRepository;

@ApplicationScoped
class TestDataCleaner {

    @Inject
    BookPanacheRepository panacheRepo;

    /**
     * Deletes all the books for tests only.
     */
    @Transactional
    public void deleteAllBooks() {
        panacheRepo.deleteAll();
    }
}