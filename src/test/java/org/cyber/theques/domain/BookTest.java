package org.cyber.theques.domain;

import org.cyber.theques.domain.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class BookTest {

    @Test
    void copyBook_should_copyDefaultData() {
        Book original = Book.builder("Clean Code", "Robert C. Martin")
            .publisher("Prentice Hall")
            .releaseDate(LocalDate.of(2008, 8, 1))
            .read(true)
            .readDate(LocalDate.of(2020, 1, 15))
            .build();

        Book copy = original.copyBook();

        assertEquals(original.getTitle(), copy.getTitle());
        assertEquals(original.getCreator(), copy.getCreator());
        assertNull(copy.getPublisher());
        assertNull(copy.getReleaseDate());
        assertFalse(copy.isConsumed());
        assertNull(copy.getConsumedDate());
    }

    @Test
    void copyBook_should_copySomeBookData() {
        Book original = Book.builder("Clean Code", "Robert C. Martin")
            .publisher("Prentice Hall")
            .releaseDate(LocalDate.of(2008, 8, 1))
            .read(true)
            .readDate(LocalDate.of(2020, 1, 15))
            .build();

        String newPublisher = "New Publisher";
        Book copy = original.copyBook(newPublisher, null, false, null);

        assertEquals(original.getTitle(), copy.getTitle());
        assertEquals(original.getCreator(), copy.getCreator());
        assertEquals(newPublisher, copy.getPublisher());
        assertNull(copy.getReleaseDate());
        assertFalse(copy.isConsumed());
        assertNull(copy.getConsumedDate());
    }

    @Test
    void copyBook_should_copyFullBookData() {
        Book original = Book.builder("Clean Code", "Robert C. Martin")
            .publisher("Prentice Hall")
            .releaseDate(LocalDate.of(2008, 8, 1))
            .read(true)
            .readDate(LocalDate.of(2020, 1, 15))
            .build();

        String newPublisher = "New Publisher";
        LocalDate newReleaseDate = LocalDate.of(2025, 1, 1);
        LocalDate newReadDate = LocalDate.now();

        Book copy = original.copyBook(newPublisher, newReleaseDate, true, newReadDate);

        assertEquals(original.getTitle(), copy.getTitle());
        assertEquals(original.getCreator(), copy.getCreator());
        assertEquals(newPublisher, copy.getPublisher());
        assertEquals(newReleaseDate, copy.getReleaseDate());
        assertTrue(copy.isConsumed());
        assertEquals(newReadDate, copy.getConsumedDate());
    }

    @Test
    void copyBook_should_notModify_originalBook() {
        String originalPublisher = "Prentice Hall";
        LocalDate originalReleaseDate = LocalDate.of(2008, 8, 1);

        Book original = Book.builder("Clean Code", "Robert C. Martin")
            .publisher(originalPublisher)
            .releaseDate(originalReleaseDate)
            .read(false)
            .build();

        String newPublisher = "New Publisher";
        LocalDate newReleaseDate = LocalDate.of(2025, 1, 1);
        LocalDate newReadDate = LocalDate.now();

        Book copy = original.copyBook(newPublisher, newReleaseDate, true, newReadDate);

        assertEquals(originalPublisher, original.getPublisher());
        assertEquals(originalReleaseDate, original.getReleaseDate());
        assertFalse(original.isConsumed());
        assertNull(original.getConsumedDate());
    }
}
