package org.cyber.theques.application;

import org.cyber.theques.domain.model.MediaItem;

import java.util.List;

public interface MediaService<T extends MediaItem> {
    List<T> getAll();
}