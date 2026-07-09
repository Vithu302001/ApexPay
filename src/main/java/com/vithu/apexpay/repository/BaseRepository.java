package com.vithu.apexpay.repository;

import java.util.List;
import java.util.Optional;

/**
 * Generic repository interface demonstrating Generics and Optional.
 *
 * @param <T>  the entity type
 * @param <ID> the identifier type
 */
public interface BaseRepository<T, ID> {

    T save(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    boolean existsById(ID id);

    void deleteById(ID id);
}
