package com.instand.domain;

/**
 * Base interface for a domain entity - an object with identity.
 */
public interface Entity<K> {

    /**
     * Returns id of this entity.
     *
     * @return id of this entity
     */
    K getId();
}
