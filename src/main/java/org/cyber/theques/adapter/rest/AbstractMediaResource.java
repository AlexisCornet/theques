package org.cyber.theques.adapter.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.cyber.theques.application.MediaService;
import org.cyber.theques.domain.model.MediaDescriptor;

import java.util.List;

/**
 * This abstract class exposes generic entry public action points for objects.
 *
 * @param <T>
 */
public abstract class AbstractMediaResource<T extends MediaDescriptor> {

    protected final MediaService<T> service;

    protected AbstractMediaResource(MediaService<T> service) {
        this.service = service;
    }

    /**
     * Returns List of all <T> objects.
     *
     * @return <T> objects in a list
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<T> getAll() {
        return service.getAll();
    }

    /**
     * Returns a single <T> object.
     *
     * @param id object's id
     * @return <T> object
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public T findById(@PathParam("id") Long id) {
        return service.findById(id)
            .orElseThrow(NotFoundException::new);
    }

    /**
     * Consumes a single <T> object.
     *
     * @param id    object's id to consume
     * @param score object's score to apply
     * @return Response indicating the result of the operation
     */
    @PATCH
    @Path("/{id}/evaluate")
    public Response evaluateMedia(@PathParam("id") Long id, @QueryParam("score") int score) {
        try {
            service.evaluate(id, score);
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * Consumes a single <T> object.
     *
     * @param id   object's id to consume
     * @param like like value to apply
     * @return Response indicating the result of the operation
     */
    @PATCH
    @Path("/{id}/like")
    public Response likeMedia(@PathParam("id") Long id, @QueryParam("like") boolean like) {
        try {
            service.like(id, like);
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}