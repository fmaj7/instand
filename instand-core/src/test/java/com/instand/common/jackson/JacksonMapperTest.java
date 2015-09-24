package com.instand.common.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class JacksonMapperTest {

    private ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());
    private JacksonMapper jm = new JacksonMapper(om);

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Foo {
        Integer id;
        String name;
        LocalDate birthDay;
    }

    String fooJson = "{\"id\":123,\"name\":\"eric\",\"birthDay\":[1980,1,1]}";
    Foo foo = new Foo(123, "eric", LocalDate.of(1980, 1, 1));

    @Test
    public void testObjectWriter() throws Exception {
        String actual = jm.objectWriter().writeValueAsString(foo);
        assertEquals(fooJson, actual);
    }

    @Test
    public void testObjectReader() throws Exception {
        Foo actual = jm.objectReader(Foo.class).readValue(fooJson);
        assertEquals(foo, actual);
    }

    @Test
    public void testSerializer() throws Exception {
        String actual = jm.serializer().serialize(foo);
        assertEquals(fooJson, actual);
    }

    @Test
    public void testDeserializer() throws Exception {
        Foo actual = jm.deserializer(Foo.class).deserialize(fooJson);
        assertEquals(foo, actual);
    }
}