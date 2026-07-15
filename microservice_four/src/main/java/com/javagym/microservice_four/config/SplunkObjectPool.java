package com.javagym.microservice_four.config;

import com.splunk.Service;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class SplunkObjectPool extends GenericObjectPool<Service> {

    public SplunkObjectPool(PooledObjectFactory<Service> factory) {
        super(factory);
    }

    public SplunkObjectPool(PooledObjectFactory<Service> factory, GenericObjectPoolConfig<Service> config) {
        super(factory, config);
    }

    public SplunkObjectPool(PooledObjectFactory<Service> factory, GenericObjectPoolConfig<Service> config, AbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }
}
