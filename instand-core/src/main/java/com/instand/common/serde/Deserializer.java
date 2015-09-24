package com.instand.common.serde;

import com.google.common.collect.Iterables;

/**
 * A generic interface that reads an object from string without throwing a checked exception, which allows object-string
 * conversions in functional processing.
 */
public interface Deserializer {

    /**
     * Return an object from its string representation.
     *
     * @param str string representation of an object
     * @return object
     * @throws RuntimeException for any error
     */
    <T> T deserialize(String str);

    /**
     * Returns the objects from their string representations.
     *
     * @param strings string representations
     * @return objects
     * @throws RuntimeException for any error
     */
    default <T> Iterable<T> deserialize(Iterable<String> strings) {
        return Iterables.transform(strings, this::deserialize);
    }
}
