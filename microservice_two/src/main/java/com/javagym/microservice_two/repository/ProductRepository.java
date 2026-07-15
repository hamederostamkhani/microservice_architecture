package com.javagym.microservice_two.repository;

import com.javagym.microservice_two.entity.ProductEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<ProductEntity, String> {

}
