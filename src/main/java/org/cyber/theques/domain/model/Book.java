package org.cyber.theques.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDate;
import java.util.Optional;

/**
 * This class matches with a Book object.
 */
@JsonDeserialize(builder = Book.Builder.class)
public class Book implements MediaItem {
    private final Long id;
    private final String title;
    private final String author;
    private final String publisher;
    private final boolean read;
    private final LocalDate releaseDate;
    private final LocalDate readDate;

    private Book(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.author = builder.author;
        this.publisher = builder.publisher;
        this.read = builder.read;
        this.releaseDate = builder.releaseDate;
        this.readDate = builder.readDate;
    }

    /**
     * Copies a book, keeping title & author.
     *
     * @return copy of the initial book with only default values
     */
    public Book copyBook() {
        return this.copyBuilder()
            .build();
    }

    /**
     * Copies a book, keeping title & author.
     *
     * @param publisher   new publisher
     * @param releaseDate new release date
     * @param read        new reading statement
     * @param readDate    new reading date
     * @return copy of the initial book with new values
     */
    public Book copyBook(String publisher, LocalDate releaseDate, boolean read, LocalDate readDate) {
        return this.copyBuilder()
            .publisher(publisher)
            .releaseDate(releaseDate)
            .read(read)
            .readDate(read ? Optional.ofNullable(readDate).orElse(LocalDate.now()) : null)
            .build();
    }

    public static Builder builder(String title, String author) {
        return new Builder(title, author);
    }

    public Builder copyBuilder() {
        return new Builder(this.title, this.author)
            .id(null);
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

    public String getPublisher() {
        return publisher;
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
        @JsonProperty("id")
        private Long id;
        @JsonProperty("title")
        private String title;
        @JsonProperty("author")
        private String author;
        @JsonProperty("publisher")
        private String publisher;
        @JsonProperty("read")
        private boolean read;
        @JsonProperty("releaseDate")
        private LocalDate releaseDate;
        @JsonProperty("readDate")
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

        public Builder publisher(String publisher) {
            this.publisher = publisher;
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
