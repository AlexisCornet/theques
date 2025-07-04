package org.cyber.theques.application;

import org.cyber.theques.domain.model.MediaItem;

import java.util.List;
import java.util.Optional;

/**
 * This interface exposes all applications relatives to any based object.
 *
 * @param <T>
 */
public interface MediaService<T extends MediaItem> {
    /**
     * Returns List of all <T> objects.
     *
     * @return <T> objects in a list
     */
    List<T> getAll();

    /**
     * Returns Optional of a single T object based on its id.
     *
     * @param id id of the object
     * @return a T object
     */
    Optional<T> findById(Long id);

    /**
     * Adds a single T object.
     *
     * @param object object to add
     */
    void add(T object);

    /**
     * Updates a single T object.
     *
     * @param updatedObject object to update
     */
    void update(T updatedObject);
}