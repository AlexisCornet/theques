package org.cyber.theques.adapter.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.cyber.theques.domain.port.MediaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Abstract infrastructure adapter providing base access operations
 * for media-related entities within the persistence layer.
 *
 * <p>This class serves as a common foundation for repository adapters
 * managing concrete media types and connects
 * the domain layer to the database via JPA and Panache.</p>
 *
 * <p>It exposes shared behavior or mapping logic applicable across multiple
 * media entity types, and may optionally offer generic filtering or ownership
 * resolution if subclassed.</p>
 *
 * <p>This class is not meant to be injected directly; instead, its concrete
 * subclasses should be used to expose media-specific persistence operations.</p>
 *
 * @param <E> the JPA entity type extending MediaEntity
 * @param <T> the corresponding domain model (e.g. Book, Movie)
 */
public abstract class MediaRepositoryAdapter<T, E> implements MediaRepository<T> {

    public static final String MEDIA_NOT_FOUND = "Media not found: ";

    protected abstract PanacheRepository<E> getPanacheRepo();

    protected abstract T toDomain(E entity);

    protected abstract E fromDomain(T item);

    protected abstract void setConsumed(E entity, LocalDate date);

    protected abstract void setScore(E entity, int score);

    protected abstract void setLike(E entity, boolean like);

    @Override
    public List<T> findAll() {
        return getPanacheRepo().listAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<T> findById(Long id) {
        return getPanacheRepo().findByIdOptional(id).map(this::toDomain);
    }

    @Transactional
    @Override
    public T save(T item) {
        E entity = fromDomain(item);
        getPanacheRepo().persist(entity);
        return toDomain(entity);
    }

    @Transactional
    @Override
    public void consume(Long id, LocalDate date) {
        Optional<E> optEntity = getPanacheRepo().findByIdOptional(id);
        if (optEntity.isEmpty()) {
            throw new NotFoundException(MEDIA_NOT_FOUND + id);
        }
        setConsumed(optEntity.get(), date);
    }

    @Transactional
    @Override
    public void evaluate(Long id, int score) {
        Optional<E> optEntity = getPanacheRepo().findByIdOptional(id);
        if (optEntity.isEmpty()) {
            throw new NotFoundException(MEDIA_NOT_FOUND + id);
        }
        setScore(optEntity.get(), score);
    }

    @Transactional
    @Override
    public void like(Long id, boolean like) {
        Optional<E> optEntity = getPanacheRepo().findByIdOptional(id);
        if (optEntity.isEmpty()) {
            throw new NotFoundException(MEDIA_NOT_FOUND + id);
        }
        setLike(optEntity.get(), like);
    }
}