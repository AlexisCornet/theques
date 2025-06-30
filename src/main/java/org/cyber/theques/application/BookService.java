package org.cyber.theques.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.cyber.theques.domain.model.Book;
import org.cyber.theques.domain.port.BookRepository;

import java.util.List;

@ApplicationScoped
public class BookService {
    @Inject
    BookRepository repository;

    public List<Book> getAll() {
        return repository.findAll();
    }

    public void add(Book book) {
        repository.save(book);
    }
}
