package org.cyber.theques.application;

import org.cyber.theques.domain.model.MediaDescriptor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Generic application service exposing core operations for media-based domain objects.
 *
 * <p>This interface defines a common set of actions applicable to any content entity
 * implementing {@link MediaDescriptor} — such as books, movies, or other consumable media.</p>
 *
 * <p>Typical operations include retrieval, insertion, consumption tracking,
 * evaluation (scoring), and preference marking (like).</p>
 *
 * @param <T> the domain type handled by the service
 */
public interface MediaService<T extends MediaDescriptor> {

    /**
     * Retrieves all persisted {@code T} objects from the data source.
     *
     * @return a list of all available media items
     */
    List<T> getAll();

    /**
     * Finds a single {@code T} object by its unique identifier.
     *
     * @param id the technical identifier of the media item
     * @return an {@link Optional} containing the item if found, or empty otherwise
     */
    Optional<T> findById(Long id);

    /**
     * Persists a new {@code T} object in the database.
     *
     * @param object the media item to be inserted
     * @return the stored media item with any generated fields populated
     */
    T add(T object);

    /**
     * Marks the specified {@code T} item as consumed on the given date.
     *
     * @param id the identifier of the media item
     * @param consumedDate the date on which it was consumed (read, watched, etc.)
     */
    void consume(Long id, LocalDate consumedDate);

    /**
     * Applies a score or rating to a {@code T} item.
     *
     * @param id the identifier of the media item
     * @param score the score value (e.g. out of 5 or 10)
     */
    void evaluate(Long id, int score);

    /**
     * Toggles a "like" or favorite state on the {@code T} item.
     *
     * @param id the identifier of the media item
     * @param like {@code true} to mark as liked; {@code false} otherwise
     */
    void like(Long id, boolean like);
}