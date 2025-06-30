package org.cyber.theques.adapter.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.cyber.theques.application.BookService;
import org.cyber.theques.domain.model.Book;
import org.cyber.theques.domain.model.NewBook;

import java.net.URI;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {
    @Inject
    BookService service;

    @GET
    public List<Book> getAll() {
        return service.getAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(List<NewBook> books, @Context UriInfo uriInfo) {
        books.forEach((NewBook book) -> {
            service.add(book);
        });

        URI location =uriInfo.getAbsolutePathBuilder().build();
        return Response.created(location).build();
    }
}