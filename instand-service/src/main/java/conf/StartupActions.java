package conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;
import ninja.lifecycle.Start;

import javax.inject.Inject;
import javax.inject.Singleton;

@Slf4j
@Singleton
public class StartupActions {

    @Inject
    ObjectMapper om;

    @Start(order = 1)
    public void start() {
        log.info("Starting service");
    }

    @Start(order = 10)
    public void configureObjectMapper() {
        om.registerModule(new JavaTimeModule());
        om.registerModule(new Jdk8Module());
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

}