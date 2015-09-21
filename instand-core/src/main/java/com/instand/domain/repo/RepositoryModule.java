package com.instand.domain.repo;

import com.google.inject.AbstractModule;

/**
 */
public class RepositoryModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SubjectRepository.class).to(InMemorySubjectRepository.class);
    }
}
