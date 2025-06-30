package org.cyber.theques.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDate;

@JsonDeserialize(builder = Book.Builder.class)
public class Book implements MediaItem {
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
        this.read = builder.consumed;
        this.releaseDate = builder.releaseDate;
        this.readDate = builder.consumedDate;
    }

    public static Builder builder(String title, String author) {
        return new Builder(title, author);
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String getCreator() {
        return author;
    }

    public String getEditor() {
        return editor;
    }

    @Override
    public boolean isConsumed() {
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
        private boolean consumed;
        private LocalDate releaseDate;
        private LocalDate consumedDate;

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
            this.consumed = read;
            return this;
        }

        public Builder releaseDate(LocalDate releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder readDate(LocalDate readDate) {
            this.consumedDate = readDate;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }

}
