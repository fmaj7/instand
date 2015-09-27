package com.instand.domain.repo.mem;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.instand.domain.Entity;
import com.instand.domain.Subject;
import com.instand.domain.repo.EntityNotFoundException;
import com.instand.domain.repo.GenericRepository;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * In-memory implementation of {@link GenericRepository}.
 */
public class InMemoryGenericRepository<K, E extends Entity<K>> implements GenericRepository<K, E> {

    protected final Map<K, E> entities = Maps.newConcurrentMap();

    /**
     * {@inheritDoc}
     */
    @Override
    public E create(@NonNull E entity) {
        ensureEntityNotExists(entity);
        entities.put(entity.getId(), entity);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<E> find(@NonNull K id) {
        E entity = entities.get(id);
        return Optional.ofNullable(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E findOrElseThrow(@NonNull K id) {
        return find(id).orElseThrow(() -> new EntityNotFoundException("Entity is not found fir id " + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<E> findAll() {
        return ImmutableList.copyOf(entities.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(@NonNull E entity) {
        ensureEntityExists(entity);
        entities.put(entity.getId(), entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(@NonNull E entity) {
        ensureEntityExists(entity);
        entities.remove(entity.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists(@NonNull K id) {
        return entities.containsKey(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(@NonNull E entity) {
        entities.put(entity.getId(), entity);
    }


    private void ensureEntityExists(@NonNull E entity) {
        checkNotNull(entity.getId(), "entity id must not be null");
        if (!entities.containsKey(entity.getId())) {
            throw new RuntimeException("Expect entity exists");
        }
    }

    private void ensureEntityNotExists(@NonNull E entity) {
        checkNotNull(entity.getId(), "entity id must not be null");
        if (entities.containsKey(entity.getId())) {
            throw new RuntimeException("Expect entity not exists");
        }
    }
}