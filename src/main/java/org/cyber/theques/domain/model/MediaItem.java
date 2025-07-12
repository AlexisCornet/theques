package org.cyber.theques.domain.model;

import java.time.LocalDate;

/**
 * This class matches with any Media object.
 */
public abstract class MediaItem implements MediaDescriptor {
    Long id;
    String title;
    LocalDate releaseDate;
    double score;
    boolean favorite;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public double getScore() {
        return score;
    }

    public boolean isFavorite() {
        return favorite;
    }
}
