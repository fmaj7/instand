package com.instand.domain.repo;

import lombok.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * A generic repository interface.
 */
public interface GenericRepository<K, E> {

    /**
     * Creates the entity.
     *
     * @param entity entity to create
     * @return entity created
     */
    E create(@NonNull E entity);

    /**
     * Finds the entity by id.
     *
     * @param id entity id
     * @return entity found
     */
    Optional<E> find(@NonNull K id);

    /**
     * Finds all entities.
     *
     * @return all entities
     */
    List<E> findAll();

    /**
     * Updates the given entity.
     *
     * @param entity entity to be updated.
     */
    void update(@NonNull E entity);

    /**
     * Creates or updates the given entity.
     *
     * @param entity entity to be created or updated
     */
    void save(@NonNull E entity);


    /**
     * Deletes the given entity.
     *
     * @param entity entity to be deleted
     */
    void delete(@NonNull E entity);

    /**
     * Return true if and only if the entity of the given id exists.
     *
     * @param id entity id
     * @return true if the entity exists
     */
    boolean exists(@NonNull K id);

}
