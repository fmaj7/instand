package com.instand.common.jackson;

import com.fasterxml.jackson.databind.ObjectReader;
import com.google.common.base.Throwables;
import com.instand.common.serde.Deserializer;
import com.instand.common.serde.Serializer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

/**
 * Implementation of {@link Serializer} backed by Jackson.
 */
@RequiredArgsConstructor
public class JacksonDeserializer implements Deserializer {

    @NonNull
    private final ObjectReader jacksonObjectReader;

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T deserialize(String str) {
        try {
            return jacksonObjectReader.readValue(str);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }
}
