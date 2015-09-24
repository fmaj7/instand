package com.instand.domain.repo.mem;

import com.google.inject.AbstractModule;
import com.instand.domain.repo.SubjectRepository;

/**
 * Provides Guice configuration for repositories through in-memory implementations.
 */
public class InMemoryRepositoryModule extends AbstractModule {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        bind(SubjectRepository.class).to(InMemorySubjectRepository.class);
    }
}
