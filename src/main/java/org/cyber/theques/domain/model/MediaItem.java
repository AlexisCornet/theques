package org.cyber.theques.domain.model;

import java.time.LocalDate;

/**
 * This interface groups all MediaItem based methods.
 */
public interface MediaItem {
    /**
     * Returns the media title.
     *
     * @return the title in a String format
     */
    String getTitle();

    /**
     * Returns the media creator.
     *
     * @return the creator in a String format  (author / development studio / etc.)
     */
    String getCreator();

    /**
     * Returns the media release date.
     *
     * @return the release date
     */
    LocalDate getReleaseDate();

    /**
     * Has the media been consumed.
     *
     * @return a boolean telling if the media had been consumed (read / played / watched / ...)
     */
    boolean isConsumed();

    /**
     * Return the date the media had been consumed.
     *
     * @return the consumed date
     */
    LocalDate getConsumedDate();
}
