package com.javagym.microservice_four.config;

import com.splunk.Service;
import com.splunk.ServiceArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SplunkObjectFactory implements PooledObjectFactory<Service> {

    @Value("${splunk.host}")
    private String host;
    @Value("${splunk.port}")
    private Integer port;
    @Value("${splunk.username}")
    private String username;
    @Value("${splunk.password}")
    private String password;


    @Override
    public void activateObject(PooledObject<Service> pooledObject) throws Exception {
    }

    @Override
    public void destroyObject(PooledObject<Service> pooledObject) throws Exception {
        pooledObject.getObject().logout();
    }

    @Override
    public PooledObject<Service> makeObject() throws Exception {
        ServiceArgs loginArgs = new ServiceArgs();
        loginArgs.setHost(host);
        loginArgs.setPort(port);
        loginArgs.setUsername(username);
        loginArgs.setPassword(password);
        log.info(host + ":" + port + " - " + username + ":" + password);
        return new DefaultPooledObject<>(Service.connect(loginArgs));
    }

    @Override
    public void passivateObject(PooledObject<Service> pooledObject) throws Exception {
    }

    @Override
    public boolean validateObject(PooledObject<Service> pooledObject) {
        return pooledObject.getObject().getSavedSearches().getName() != null;
    }
}
