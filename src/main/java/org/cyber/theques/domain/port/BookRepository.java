package org.cyber.theques.domain.port;

import org.cyber.theques.domain.model.Book;

import java.util.List;

public interface BookRepository {
    List<Book> findAll();

    void save(Book book);
}
