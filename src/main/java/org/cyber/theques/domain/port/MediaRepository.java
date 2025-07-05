package org.cyber.theques.domain.port;

import java.util.List;
import java.util.Optional;

public interface MediaRepository<T> {
    /**
     * Returns the list of all T objects.
     *
     * @return a T object list
     */
    List<T> findAll();

    /**
     * Returns Optional of a single T object based on its id.
     *
     * @param id id of the object
     * @return a T object
     */
    Optional<T> findById(Long id);

    /**
     * Saves a new T object in the database.
     *
     * @param object object to save
     */
    void save(T object);

}
