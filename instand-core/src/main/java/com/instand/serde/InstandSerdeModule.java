package com.instand.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.instand.common.jackson.JacksonMapper;

import javax.inject.Singleton;

/**
 * Guice module to provides {@link ObjectMapper} singleton.
 */
public class InstandSerdeModule extends AbstractModule {

    @Override
    protected void configure() {}

    @Provides
    @Singleton
    JacksonMapper jacksonMapper(ObjectMapper om) {
        return new JacksonMapper(om);
    }

    @Provides
    @Singleton
    ObjectMapper objectMapper() {
        // configure the ObjectMapper for Instand specific configuration
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(new Jdk8Module())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
}
