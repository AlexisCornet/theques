package org.cyber.theques.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.cyber.theques.domain.model.Book;
import org.cyber.theques.domain.port.BookRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public Book add(Book book) {
        return repository.save(book);
    }

    @Override
    public void consume(Long id, LocalDate readDate) {
        repository.consume(id, readDate);
    }

    @Override
    public void evaluate(Long id, int score) {
        repository.evaluate(id, score);
    }

    @Override
    public void like(Long id, boolean like) {
        repository.like(id, like);
    }
}
