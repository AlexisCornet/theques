package org.cyber.theques.adapter.persistence.entity;

import jakarta.persistence.Entity;

import java.time.LocalDate;

/**
 * This class matches with a Book entity.
 */
@Entity
public class BookEntity extends MediaEntity {
    public String author;
    public String publisher;
    public boolean read;
    public LocalDate readDate;
}
