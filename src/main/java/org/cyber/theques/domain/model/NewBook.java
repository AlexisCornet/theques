package org.cyber.theques.domain.model;

public class NewBook {
    public String title;
    public String author;
    public boolean read;

    public NewBook() {
    }

    public NewBook(String title, String author) {
        this(title, author, false);
    }

    public NewBook(String title, String author, boolean read) {
        this.title = title;
        this.author = author;
        this.read = read;
    }
}
