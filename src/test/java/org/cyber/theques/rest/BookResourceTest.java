package org.cyber.theques.rest;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.cyber.theques.adapter.rest.BookResource;
import org.cyber.theques.application.BookService;
import org.cyber.theques.domain.model.Book;
import org.cyber.theques.domain.port.BookRepository;
import org.cyber.theques.utils.TestDataCleaner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;

@QuarkusTest
@TestHTTPEndpoint(BookResource.class)
public class BookResourceTest {

    @Inject
    BookService service;

    @Inject
    BookRepository repository;

    @Inject
    TestDataCleaner cleaner;

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
            .build();
    }

    @AfterEach
    void cleanup() {
        cleaner.deleteAllBooks();
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
    void findById_should_throw_notFoundException() {
        given()
            .when()
            .get("/{id}", 666L)
            .then()
            .statusCode(404);
    }


    @Test
    void should_return_book_by_id() {
        repository.save(book);
        Book inserted = service.getAll().getFirst();
        Long bookId = inserted.getId();

        given()
            .when()
            .get("/{id}", bookId)
            .then()
            .statusCode(200);
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
        given()
            .patch("/{id}/read", bookId)
            .then()
            .statusCode(404);
    }

    @Test
    void readBook_should_markBookAsRead_withMissingDate() {
        repository.save(book);
        Book inserted = service.getAll().getFirst();
        Long bookId = inserted.getId();

        given()
            .patch("/{id}/read", bookId)
            .then()
            .statusCode(200);
    }

    @Test
    void readBook_should_markBookAsRead_withQueryDate() {
        LocalDate readDate = LocalDate.of(2025, 7, 5);
        repository.save(book);
        Book inserted = service.getAll().getFirst();
        Long bookId = inserted.getId();

        given()
            .patch("/{id}/read?readDate={date}", bookId, readDate.toString())
            .then()
            .statusCode(200);
    }
}
