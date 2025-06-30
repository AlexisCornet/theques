package org.cyber.theques.infra;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.cyber.theques.adapter.persistence.entity.BookEntity;
import org.cyber.theques.adapter.persistence.repository.BookPanacheRepository;
import org.cyber.theques.adapter.persistence.repository.BookRepositoryAdapter;
import org.cyber.theques.domain.model.Book;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;


@QuarkusTest
public class BookRepositoryAdapterTest {
    @Inject
    BookRepositoryAdapter adapter;

    @InjectMock
    BookPanacheRepository panacheRepo;

    @Test
    void save_should_persistInDatabase() {
        String title = "myBook";
        String author = "The Author";
        boolean read = true;
        Book book = new Book(title, author, read);

        adapter.save(book);
        verify(panacheRepo).persist(any(BookEntity.class));
    }
}
