package org.cyber.theques.application;

import org.cyber.theques.domain.model.Book;
import org.cyber.theques.domain.port.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @InjectMocks
    BookService service;

    @Mock
    BookRepository repository;

    String title;
    String author;
    Book book;

    @BeforeEach
    void setUp() {
        title = "Clean Code";
        author = "Robert C. Martin";
        book = Book.builder(title, author).build();
    }

    @Test
    void findById_should_returnBook_when_present() {
        Book book = new Book.Builder("Refactoring", "Martin Fowler")
            .id(5L)
            .build();

        when(repository.findById(5L)).thenReturn(Optional.of(book));

        Optional<Book> result = service.findById(5L);

        assertTrue(result.isPresent());
        assertEquals("Refactoring", result.get().getTitle());
        verify(repository).findById(5L);
    }

    @Test
    void findById_should_returnEmpty_when_notFound() {
        when(repository.findById(42L)).thenReturn(Optional.empty());

        Optional<Book> result = service.findById(42L);

        assertTrue(result.isEmpty());
        verify(repository).findById(42L);
    }

    @Test
    void add_should_saveNewbook() {
        service.add(book);

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(repository).save(captor.capture());

        Book insertedBook = captor.getValue();
        assertEquals(title, insertedBook.getTitle());
        assertEquals(author, insertedBook.getCreator());
        assertFalse(insertedBook.isConsumed());
    }

    @Test
    void consume_should_save() {
        Long bookId = 1L;
        LocalDate today = LocalDate.now();
        service.consume(bookId, today);
        verify(repository).consume(bookId, today);
    }

}