package org.cyber.theques.domain.model;

import java.time.LocalDate;

public class Book {
    public String title;
    public String author;
    public boolean read;
    public LocalDate addDate;
    public LocalDate upDate;

    public Book() {
    }

    public Book(String title, String author, boolean read) {
        this.title = title;
        this.author = author;
        this.read = read;
        this.addDate = LocalDate.now();
        this.upDate = null;
    }

    public Book(NewBook book) {
        this.title = book.title;
        this.author = book.author;
        this.read = book.read;
        this.addDate = LocalDate.now();
        this.upDate = null;
    }
}

