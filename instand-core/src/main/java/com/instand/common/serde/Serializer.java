package com.instand.common.serde;

import com.google.common.collect.Iterables;

/**
 * A generic interface that writing an object to string without throwing a checked exception, which allows object-string
 * conversions in functional processing.
 */
public interface Serializer {

    /**
     * Return a string representation from an object.
     *
     * @param obj object
     * @return string representation
     */
    String serialize(Object obj);

    /**
     * Return string representations from objects.
     *
     * @param objects objects
     * @param <T> type of the object Iterable
     * @return string representations
     */
    default <T> Iterable<String> serialize(Iterable<T> objects) {
        return Iterables.transform(objects, this::serialize);
    }
}
