package org.cyber.theques.adapter.persistence.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class BookEntity extends PanacheEntity {
    public String title;
    public String author;
    public boolean read;
    public LocalDate addDate;
    public LocalDate upDate;
}
