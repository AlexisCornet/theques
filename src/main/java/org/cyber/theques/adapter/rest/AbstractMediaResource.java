package org.cyber.theques.adapter.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.cyber.theques.application.MediaService;
import org.cyber.theques.domain.model.MediaItem;

import java.util.List;

public abstract class AbstractMediaResource<T extends MediaItem> {

    protected final MediaService<T> service;

    protected AbstractMediaResource(MediaService<T> service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<T> getAll() {
        return service.getAll();
    }
}