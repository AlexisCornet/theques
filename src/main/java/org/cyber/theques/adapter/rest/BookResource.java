package org.cyber.theques.adapter.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.NotFoundException;
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

    @POST
    @Path("/copy-empty")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response copyBook(Book sourceBook) {
        Book inserted = service.add(sourceBook.copyBook());
        return Response.status(Response.Status.CREATED).entity(inserted).build();
    }

    @POST
    @Path("/copy-full")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response copyFullBook(Book sourceBook) {
        Book copy = sourceBook.copyBook(
            sourceBook.getPublisher(),
            sourceBook.getReleaseDate(),
            sourceBook.isConsumed(),
            sourceBook.getConsumedDate()
        );
        Book inserted = service.add(copy);
        System.out.println("book copié : " + inserted);
        return Response.status(Response.Status.CREATED).entity(inserted).build();
    }

    /**
     * Updates a Book when it is read
     *
     * @param id          id of Book to update
     * @param readDate reading date
     * @return the response of updating the book
     */
    @PATCH
    @Path("/{id}/read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consumeMedia(@PathParam("id") Long id, @QueryParam("readDate") LocalDate readDate) {
        try {
            service.consume(id, readDate);
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}