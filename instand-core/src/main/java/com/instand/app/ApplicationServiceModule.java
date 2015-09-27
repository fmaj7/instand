package com.instand.app;

import com.google.inject.AbstractModule;
import com.instand.domain.repo.mem.InMemoryRepositoryModule;

public class ApplicationServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new InMemoryRepositoryModule());
        bind(InstandApplicationService.class).to(InstandApplicationServiceImpl.class);
    }
}
