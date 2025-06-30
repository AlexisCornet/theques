package org.cyber.theques.adapter.persistence.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.cyber.theques.adapter.persistence.entity.BookEntity;
import org.cyber.theques.domain.model.Book;
import org.cyber.theques.domain.port.BookRepository;

import java.util.List;

@ApplicationScoped
public class BookRepositoryAdapter implements BookRepository {

    @Inject
    BookPanacheRepository panacheRepo;

    @Override
    public List<Book> findAll() {
        return panacheRepo.listAll().stream().map(this::toDomain).toList();
    }

    @Transactional
    @Override
    public void save(Book book) {
        panacheRepo.persist(fromDomain(book));
    }

    private Book toDomain(BookEntity bookEntity) {
        Book book = new Book();
        book.title = bookEntity.title;
        book.author = bookEntity.author;
        book.read = bookEntity.read;
        book.addDate = bookEntity.addDate;
        book.upDate = bookEntity.upDate;
        return book;
    }

    private BookEntity fromDomain(Book bookDomain) {
        BookEntity book = new BookEntity();
        book.title = bookDomain.title;
        book.author = bookDomain.author;
        book.read = bookDomain.read;
        book.addDate = bookDomain.addDate;
        book.upDate = bookDomain.upDate;
        return book;
    }

}
