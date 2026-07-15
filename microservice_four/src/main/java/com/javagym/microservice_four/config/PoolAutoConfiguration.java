package com.javagym.microservice_four.config;

import com.splunk.SSLSecurityProtocol;
import com.splunk.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PoolAutoConfiguration {

    @Bean
    public SplunkObjectPool debugConnectionPool(SplunkPoolConfig splunkPoolConfig, SplunkObjectFactory splunkObjectFactory) {
        Service.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);
        splunkPoolConfig.setJmxEnabled(false);
        return new SplunkObjectPool(splunkObjectFactory, splunkPoolConfig);
    }
}