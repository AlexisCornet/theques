package org.cyber.theques.infra;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.cyber.theques.adapter.persistence.entity.BookEntity;
import org.cyber.theques.adapter.persistence.repository.BookPanacheRepository;
import org.cyber.theques.adapter.persistence.repository.BookRepositoryAdapter;
import org.cyber.theques.domain.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
public class BookRepositoryAdapterTest {
    @Inject
    BookRepositoryAdapter adapter;

    @InjectMock
    BookPanacheRepository panacheRepo;

    Book book;
    Long id;
    String title;
    String author;

    @BeforeEach
    void setUp() {
        id = 1L;
        title = "myBook";
        author = "The Author";

        book = Book.builder(title, author).id(id).build();
    }

    @Test
    void findById_should_return_empty_noBook() {
        when(panacheRepo.findByIdOptional(anyLong())).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), adapter.findById(1L));
    }

    @Test
    void findById_should_return_book() {
        BookEntity entity = new BookEntity();
        entity.id = 1L;
        entity.title = title;
        entity.author = author;

        when(panacheRepo.findByIdOptional(anyLong())).thenReturn(Optional.of(entity));

        Optional<Book> result = adapter.findById(1L);
        assertTrue(result.isPresent());
        Book resultBook = result.get();
        assertEquals(id, resultBook.getId());
        assertEquals(title, resultBook.getTitle());
        assertEquals(author, resultBook.getCreator());
    }

    @Test
    void save_should_persistInDatabase() {
        adapter.save(book);
        verify(panacheRepo).persist(any(BookEntity.class));
    }

    @Test
    void consume_should_throw_entityNotFoundException_noBook() {
        when(panacheRepo.findByIdOptional(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> adapter.consume(id, LocalDate.now()));
    }

    @Test
    void consume_should_update_readData() {
        LocalDate today = LocalDate.now();
        BookEntity entity = new BookEntity();
        entity.id = 1L;
        entity.title = title;
        entity.author = author;

        when(panacheRepo.findByIdOptional(anyLong())).thenReturn(Optional.of(entity));
        adapter.consume(id, today);

        assertTrue(entity.read);
        assertEquals(today, entity.readDate);
    }
}
