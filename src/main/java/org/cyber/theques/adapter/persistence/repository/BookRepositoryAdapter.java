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
        return Book.builder(bookEntity.title, bookEntity.author)
            .editor(bookEntity.editor)
            .read(bookEntity.read)
            .releaseDate(bookEntity.releaseDate)
            .readDate(bookEntity.readDate)
            .build();
    }

    private BookEntity fromDomain(Book book) {
        BookEntity entity = new BookEntity();
        entity.title = book.getTitle();
        entity.author = book.getCreator();
        entity.editor = book.getEditor();
        entity.read = book.isConsumed();
        entity.releaseDate = book.getReleaseDate();
        entity.readDate = book.getReadDate();

        return entity;
    }

}
