package org.cyber.theques.rest;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.cyber.theques.adapter.rest.BookResource;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@QuarkusTest
@TestHTTPEndpoint(BookResource.class)
public class BookResourceTest {

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
            .contentType("application/json")
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
}
