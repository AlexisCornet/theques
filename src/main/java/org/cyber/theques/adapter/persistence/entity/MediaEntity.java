package org.cyber.theques.adapter.persistence.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDate;

/**
 * This class matches with any Media entity.
 */
@MappedSuperclass
public class MediaEntity extends PanacheEntity {
    public String title;
    public LocalDate releaseDate;
    public double score;
    public boolean favorite;
}
