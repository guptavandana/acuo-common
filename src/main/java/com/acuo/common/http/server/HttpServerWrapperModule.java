/*
 * Copyright (c) 2012 Palomino Labs, Inc.
 */

package com.acuo.common.http.server;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.servlet.GuiceFilter;

/**
 * Binds an HTTP server with Guice servlet, Jackson and authentication support.
 */
public class HttpServerWrapperModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GuiceFilter.class);
        install(new FactoryModuleBuilder()
            .implement(HttpServerWrapper.class, HttpServerWrapper.class)
            .build(HttpServerWrapperFactory.class));
    }
}
