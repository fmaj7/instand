package com.instand.domain.repo.prod;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.instand.domain.Subject;
import com.instand.domain.repo.SubjectRepository;
import com.instand.serde.InstandSerdeModule;
import org.junit.Test;

import java.time.Instant;

/**
 */
public class DynamoSubjectRepositoryTest {

    @Test
    public void create() {
        Injector injector = Guice.createInjector(new ProductionRepositoryModule(), new InstandSerdeModule());
        SubjectRepository repository = injector.getInstance(SubjectRepository.class);

        Subject subject = Subject.builder()
                .id("foobarid2")
                .createdAt(Instant.now())
                .createdByUserId("userfoo")
                .title("hello world")
                .build();

        Subject s = repository.create(subject);
        Subject result = repository.findOrElseThrow(subject.getId());
        System.out.println(result);
    }
}
