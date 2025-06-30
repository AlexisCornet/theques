package org.cyber.theques.application;

import org.cyber.theques.domain.model.Book;
import org.cyber.theques.domain.port.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @InjectMocks
    BookService service;

    @Mock
    BookRepository repository;

    @Test
    void add_should_saveNewbook() {
        String title = "myBook";
        String author = "The Author";
        Book book = Book.builder(title, author).build();
        service.add(book);

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        Mockito.verify(repository).save(captor.capture());

        Book insertedBook=captor.getValue();
        assertEquals(title, insertedBook.getTitle());
        assertEquals(author, insertedBook.getCreator());
        assertFalse(insertedBook.isConsumed());
    }

}