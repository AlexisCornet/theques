package org.cyber.theques.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDate;

/**
 * This class matches with a Book object.
 */
@JsonDeserialize(builder = Book.Builder.class)
public class Book implements MediaItem {
    private final Long id;
    private final String title;
    private final String author;
    private final String editor;
    private final boolean read;
    private final LocalDate releaseDate;
    private final LocalDate readDate;

    private Book(Builder builder) {
        this.id = builder.id;
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

    public Builder copyBuilder() {
        return new Builder(this.title, this.author)
            .id(this.id)
            .editor(this.editor)
            .releaseDate(this.releaseDate)
            .read(this.read)
            .readDate(this.readDate);
    }

    /**
     * Updates the Book to put it at read.
     *
     * @param readDate when the book had been read
     * @return a Book object with read & readDate updated
     */
    public Book withRead(LocalDate readDate) {
        return this.copyBuilder()
            .read(true)
            .readDate(readDate)
            .build();
    }

    public Long getId() {
        return id;
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
    @JsonProperty("read")
    public boolean isConsumed() {
        return read;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    @Override
    @JsonProperty("readDate")
    public LocalDate getConsumedDate() {
        return readDate;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private Long id;
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

        public Builder id(Long id) {
            this.id = id;
            return this;
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
