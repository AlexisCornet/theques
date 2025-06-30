package org.cyber.theques.adapter.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.cyber.theques.adapter.persistence.entity.BookEntity;

@ApplicationScoped
public class BookPanacheRepository implements PanacheRepository<BookEntity> {
}
