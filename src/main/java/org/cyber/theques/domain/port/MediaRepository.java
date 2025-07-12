package org.cyber.theques.domain.port;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MediaRepository<T> {
    /**
     * Returns the list of all <T> objects.
     *
     * @return a <T> object list
     */
    List<T> findAll();

    /**
     * Returns Optional of a single <T> object based on its id.
     *
     * @param id id of the object
     * @return a <T> object
     */
    Optional<T> findById(Long id);

    /**
     * Saves a new <T> object in the database.
     *
     * @param object object to save
     * @return <T> single object inserted in database
     */
    T save(T object);

    /**
     * Consumes a media based on its id
     *
     * @param id          media id
     * @param consumeDate consumed date
     */
    void consume(Long id, LocalDate consumeDate);

    /**
     * Evaluates a <T> object.
     *
     * @param id    object's id to evaluate
     * @param score score value
     */
    void evaluate(Long id, int score);

    /**
     * Like a <T> object.
     *
     * @param id   object's id to like
     * @param like like value to apply
     */
    void like(Long id, boolean like);
}
