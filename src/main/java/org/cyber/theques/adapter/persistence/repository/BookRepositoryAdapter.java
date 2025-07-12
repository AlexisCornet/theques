package org.cyber.theques.adapter.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.cyber.theques.adapter.persistence.entity.BookEntity;
import org.cyber.theques.domain.model.Book;
import org.cyber.theques.domain.port.BookRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter providing database access to {@link BookEntity} instances
 * and exposing domain-level {@link Book} objects to the application layer.
 *
 * <p>This class implements persistence logic specific to books, such as filtering
 * by user, retrieving favorites, or executing copy operations from stored entities.</p>
 *
 * <p>It translates between {@link BookEntity} and {@link Book} using a dedicated mapper,
 * and respects domain constraints such as ownership and read status.</p>
 *
 * <p>Usually injected as an implementation of a port like {@code BookQueryPort}
 * or {@code BookCommandPort} in the service layer.</p>
 *
 * <p>Typical operations include:
 * <ul>
 *   <li>Finding a book by ID and user</li>
 *   <li>Listing books marked as read or favorite</li>
 *   <li>Persisting copied or new books</li>
 * </ul>
 * </p>
 *
 * @see BookEntity
 * @see Book
 * @see MediaRepositoryAdapter
 */
@ApplicationScoped
public class BookRepositoryAdapter extends MediaRepositoryAdapter<Book, BookEntity> implements BookRepository {

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

    @Override
    protected void setConsumed(BookEntity entity, LocalDate date) {
        entity.read = true;
        entity.readDate = Optional.ofNullable(date).orElse(LocalDate.now());
    }

    @Override
    protected void setScore(BookEntity entity, int score) {
        entity.score = score;
    }

    @Override
    protected void setLike(BookEntity entity, boolean like) {
        entity.favorite = like;
    }

    @Override
    protected PanacheRepository<BookEntity> getPanacheRepo() {
        return panacheRepo;
    }

    @Override
    protected Book toDomain(BookEntity bookEntity) {
        return Book.builder(bookEntity.title, bookEntity.author)
            .id(bookEntity.id)
            .publisher(bookEntity.publisher)
            .read(bookEntity.read)
            .releaseDate(bookEntity.releaseDate)
            .readDate(bookEntity.readDate)
            .score(bookEntity.score)
            .favorite(bookEntity.favorite)
            .build();
    }

    @Override
    protected BookEntity fromDomain(Book book) {
        BookEntity entity = new BookEntity();
        entity.id = book.getId();
        entity.title = book.getTitle();
        entity.author = book.getCreator();
        entity.publisher = book.getPublisher();
        entity.read = book.isConsumed();
        entity.releaseDate = book.getReleaseDate();
        entity.readDate = book.getConsumedDate();
        entity.score = book.getScore();
        entity.favorite = book.isFavorite();
        return entity;
    }

}
