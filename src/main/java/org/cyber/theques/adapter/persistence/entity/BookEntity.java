package org.cyber.theques.adapter.persistence.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.LocalDate;

/**
 * This class matches with a Book entity.
 */
@Entity
public class BookEntity extends PanacheEntity {
    public String title;
    public String author;
    public String editor;
    public boolean read;
    public LocalDate releaseDate;
    public LocalDate readDate;
}
