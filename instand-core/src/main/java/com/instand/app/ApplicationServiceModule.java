package com.instand.app;

import com.google.inject.AbstractModule;
import com.instand.domain.repo.RepositoryModule;

public class ApplicationServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new RepositoryModule());
        bind(InstandApplicationService.class).to(InstandApplicationServiceImpl.class);
    }
}
