package org.cyber.theques.domain.model;

import java.time.LocalDate;

public interface MediaItem {
    String getTitle();

    String getCreator();

    LocalDate getReleaseDate();

    boolean isConsumed();

}
