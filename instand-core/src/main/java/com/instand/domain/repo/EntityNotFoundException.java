package com.instand.domain.repo;

/**
 * Thrown then an entity is not found.
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Class<?> entityClass, String id, Throwable cause) {
        this(String.format("%s is not found for id %s", entityClass, id), cause);
    }

    public EntityNotFoundException(Class<?> entityClass, String id) {
        super(String.format("%s is not found for id %s", entityClass, id));
    }
}
