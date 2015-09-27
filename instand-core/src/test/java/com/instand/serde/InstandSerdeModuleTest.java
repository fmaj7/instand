package com.instand.serde;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.instand.common.jackson.JacksonMapper;
import org.junit.Test;

import static org.junit.Assert.*;

public class InstandSerdeModuleTest {

    @Test
    public void testJacksonMapper() throws Exception {
        Injector injector = Guice.createInjector(new InstandSerdeModule());
        JacksonMapper jm = injector.getInstance(JacksonMapper.class);
        assertNotNull(jm);
    }
}