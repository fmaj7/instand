package com.instand.app;

import com.google.inject.AbstractModule;
import com.instand.dataaccess.repo.prod.ProductionRepositoryModule;

public class ApplicationServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new ProductionRepositoryModule());
        bind(InstandApplicationService.class).to(InstandApplicationServiceImpl.class);
    }
}
