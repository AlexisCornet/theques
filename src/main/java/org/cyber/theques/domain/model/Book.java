package org.cyber.theques.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDate;

@JsonDeserialize(builder = Book.Builder.class)
public class Book {
    private final String title;
    private final String author;
    private final String editor;
    private final boolean read;
    private final LocalDate releaseDate;
    private final LocalDate readDate;

    private Book(Builder builder) {
        this.title = builder.title;
        this.author = builder.author;
        this.editor = builder.editor;
        this.read = builder.read;
        this.releaseDate = builder.releaseDate;
        this.readDate = builder.readDate;
    }

    public static Builder builder(String title, String author) {
        return new Builder(title, author);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getEditor() {
        return editor;
    }

    public boolean isRead() {
        return read;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public LocalDate getReadDate() {
        return readDate;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private String title;
        private String author;
        private String editor;
        private boolean read;
        private LocalDate releaseDate;
        private LocalDate readDate;

        public Builder() {
        }

        public Builder(String title, String author) {
            this.title = title;
            this.author = author;
        }

        public Builder editor(String editor) {
            this.editor = editor;
            return this;
        }

        public Builder read(boolean read) {
            this.read = read;
            return this;
        }

        public Builder releaseDate(LocalDate releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder readDate(LocalDate readDate) {
            this.readDate = readDate;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }

}
