package org.cyber.theques.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.cyber.theques.domain.model.Book;
import org.cyber.theques.domain.port.BookRepository;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This service represents all possible actions on a Book.
 */
@ApplicationScoped
public class BookService implements MediaService<Book> {
    @Inject
    BookRepository repository;

    @Override
    public List<Book> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void add(Book book) {
        repository.save(book);
    }

    @Override
    public void update(Book book) {
        checkNotNull(book.getId(), "Cannot update a book with no ID");
        repository.save(book);
    }
}
