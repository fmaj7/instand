package com.instand.common.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.instand.common.serde.Deserializer;
import com.instand.common.serde.Serializer;
import lombok.RequiredArgsConstructor;


/**
 * A replacement for Jackson's {@link ObjectMapper} to be used in Instand codebase.
 * <p>
 * First, unlike {@link ObjectMapper}, it is immutable. It exposes {@link ObjectMapper} serialization and deserialization
 * functionality through the immutable {@link ObjectReader} and {@link ObjectWriter} objects.
 * <p>
 * Second, it provides {@link Serializer} and {@link Deserializer} which provides convenient APIs for serialization
 * and deserialization.
 */
@RequiredArgsConstructor
public class JacksonMapper {

    /**
     * Wrapped {@link ObjectMapper}.
     */
    private final ObjectMapper om;

    /**
     * Returns the immutable Jackson {@link ObjectWriter}.
     *
     * @return the Jackson {@link ObjectWriter}
     */
    public ObjectWriter objectWriter() {
        return om.writer();
    }

    /**
     * Returns the immutable Jackson {@link ObjectReader}.
     *
     * @return the Jackson {@link ObjectReader}
     */
    public <T> ObjectReader objectReader(Class<T> clazz) {
        return om.readerFor(clazz);
    }

    /**
     * Returns a {@link Serializer}.
     *
     * @return a {@link Serializer}
     */
    public Serializer serializer() {
        return new JacksonSerializer(objectWriter());
    }

    /**
     * Returns a {@link Deserializer}.
     *
     * @return a {@link Deserializer}
     */
    public <T> Deserializer deserializer(Class<T> clazz) {
        return new JacksonDeserializer(objectReader(clazz));
    }

}
