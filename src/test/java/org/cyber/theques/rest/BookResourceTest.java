package org.cyber.theques.rest;

import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.cyber.theques.adapter.rest.BookResource;
import org.cyber.theques.application.BookService;
import org.cyber.theques.domain.model.Book;
import org.cyber.theques.domain.port.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestHTTPEndpoint(BookResource.class)
public class BookResourceTest {

    @Inject
    BookService service;

    @InjectMock
    BookRepository repository;

    Long bookId;
    String title;
    String author;
    Book book;

    @BeforeEach
    void setUp() {
        bookId = 1L;
        title = "Clean Code";
        author = "Robert C. Martin";
        book = new Book.Builder(title, author)
            .id(bookId)
            .build();
    }

    @Test
    void getAll_should_return_books() {
        given()
            .when()
            .get()
            .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    void add_should_addNewBookToTheList() {
        given()
            .contentType(ContentType.JSON)
            .body("""
                [{
                    "title":"myBook",
                    "author":"The Author"
                }]
                """
            )
            .when()
            .post()
            .then()
            .statusCode(201);
    }

    @Test
    void readBook_should_return400_invalidDate() {
        given()
            .patch("/{id}/read?readDate=not-a-date", bookId)
            .then()
            .statusCode(greaterThanOrEqualTo(400))
            .statusCode(lessThan(500));
    }

    @Test
    void readBook_should_return404_notFoundBook() {
        when(repository.findById(bookId)).thenReturn(Optional.empty());

        given()
            .patch("/{id}/read?readDate=2025-07-05", bookId)
            .then()
            .statusCode(404);
    }

    @Test
    void readBook_should_markBookAsRead_withMissingDate() {
        LocalDate today = LocalDate.now();
        Book updated = book.withRead(today);

        when(repository.findById(bookId)).thenReturn(Optional.of(book));
        doNothing().when(repository).save(updated);

        given()
            .patch("/{id}/read", bookId)
            .then()
            .statusCode(200)
            .body("title", equalTo(title))
            .body("read", is(true))
            .body("readDate", equalTo(today.toString()));
    }

    @Test
    void readBook_should_markBookAsRead_withQueryDate() {
        LocalDate readDate = LocalDate.of(2025, 7, 5);
        Book updated = book.withRead(readDate);

        when(repository.findById(bookId)).thenReturn(Optional.of(book));
        doNothing().when(repository).save(updated);

        given()
            .patch("/{id}/read?readDate={date}", bookId, readDate.toString())
            .then()
            .statusCode(200)
            .body("title", equalTo(title))
            .body("read", is(true))
            .body("readDate", equalTo(readDate.toString()));
    }
}
