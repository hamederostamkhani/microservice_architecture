package com.javagym.microservice_two.repository;

import com.javagym.microservice_two.entity.SellerEntity;
import com.javagym.microservice_two.entity.SellerProduct;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SellerProductRepository extends ReactiveMongoRepository<SellerProduct, String> {
    @Query("{ 'seller': ?0}")
    Mono<SellerProduct> findBySeller(SellerEntity seller);
}
