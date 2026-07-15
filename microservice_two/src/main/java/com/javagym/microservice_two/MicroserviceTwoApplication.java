package com.javagym.microservice_two;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class MicroserviceTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceTwoApplication.class, args);
    }

}
