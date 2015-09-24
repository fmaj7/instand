package com.instand.common.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.base.Throwables;
import com.instand.common.serde.Serializer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Implementation of {@link Serializer} backed by Jackson.
 */
@RequiredArgsConstructor
public class JacksonSerializer implements Serializer {

    @NonNull
    private final ObjectWriter jacksonObjectWriter;

    /**
     * {@inheritDoc}
     */
    @Override
    public String serialize(Object obj) {
        try {
            return jacksonObjectWriter.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw Throwables.propagate(e);
        }
    }
}
