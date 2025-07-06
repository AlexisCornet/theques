package org.cyber.theques.adapter.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.cyber.theques.application.BookService;
import org.cyber.theques.domain.model.Book;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

/**
 * This class exposes entry public action points for Book objects.
 */
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource extends AbstractMediaResource<Book> {

    private final BookService service;

    @Inject
    public BookResource(BookService service) {
        super(service);
        this.service = service;
    }

    /**
     * Adds a collection of books.
     *
     * @param books   list of books
     * @param uriInfo URI Context
     * @return the response of adding books
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(List<Book> books, @Context UriInfo uriInfo) {
        books.forEach(service::add);

        URI location = uriInfo.getAbsolutePathBuilder().build();
        return Response.created(location).build();
    }

    /**
     * Updates a book when it is read
     *
     * @param id       id of book to update
     * @param readDate read date
     * @return the response of updating the book
     */
    @PATCH
    @Path("/{id}/read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readBook(@PathParam("id") Long id, @QueryParam("readDate") LocalDate readDate) {
        service.consume(id, readDate);
        return Response.ok().build();
    }
}