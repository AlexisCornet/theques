package org.cyber.theques.adapter.persistence.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.cyber.theques.adapter.persistence.entity.BookEntity;
import org.cyber.theques.domain.model.Book;
import org.cyber.theques.domain.port.BookRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookRepositoryAdapter implements BookRepository {

    @Inject
    BookPanacheRepository panacheRepo;

    @Override
    public List<Book> findAll() {
        return panacheRepo.listAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return panacheRepo.findByIdOptional(id).map(this::toDomain);
    }

    @Transactional
    @Override
    public Book save(Book book) {
        BookEntity entity = fromDomain(book);
        panacheRepo.persist(entity);
        return toDomain(entity);
    }

    @Transactional
    @Override
    public void consume(Long id, LocalDate readDate) {
        Optional<BookEntity> optEntity = panacheRepo.findByIdOptional(id);
        if (optEntity.isEmpty()) {
            throw new NotFoundException("Book not found: " + id);
        }
        optEntity.get().read = true;
        optEntity.get().readDate = Optional.ofNullable(readDate).orElse(LocalDate.now());
    }

    private Book toDomain(BookEntity bookEntity) {
        return Book.builder(bookEntity.title, bookEntity.author)
            .id(bookEntity.id)
            .publisher(bookEntity.publisher)
            .read(bookEntity.read)
            .releaseDate(bookEntity.releaseDate)
            .readDate(bookEntity.readDate)
            .build();
    }

    private BookEntity fromDomain(Book book) {
        BookEntity entity = new BookEntity();
        entity.id = book.getId();
        entity.title = book.getTitle();
        entity.author = book.getCreator();
        entity.publisher = book.getPublisher();
        entity.read = book.isConsumed();
        entity.releaseDate = book.getReleaseDate();
        entity.readDate = book.getConsumedDate();

        return entity;
    }

}
