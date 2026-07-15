package com.javagym.microservice_four.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "splunk.pool")
public class SplunkPoolConfig extends GenericObjectPoolConfig {
}