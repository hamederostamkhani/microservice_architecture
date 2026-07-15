package com.javagym.microservice_one.repository;

import com.javagym.microservice_one.entity.SellerEntity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface SellerRepository extends ReactiveSortingRepository<SellerEntity, Long> {

}
