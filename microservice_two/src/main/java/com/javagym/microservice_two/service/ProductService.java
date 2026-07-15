package com.javagym.microservice_two.service;

import com.javagym.microservice_two.entity.dto.ProductDTO;
import com.javagym.microservice_two.entity.ProductEntity;
import com.javagym.microservice_two.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<String> addProduct(ProductDTO productDTO) {
        return productRepository.save(map(productDTO)).map(ProductEntity::getId);
    }

    public Flux<ProductDTO> getAll() {
        return productRepository.findAll().map(this::map);
    }

    private ProductEntity map(ProductDTO productDTO) {
        if (productDTO == null)
            return null;
        ProductEntity productEntity = new ProductEntity();
        productEntity.setTitle(productDTO.getTitle());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setCategory(productDTO.getCategory());
        productEntity.setImage(productDTO.getImage());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setRating(productDTO.getRating());
        productEntity.setValidFrom(new Date());
        return productEntity;
    }

    private ProductDTO map(ProductEntity productEntity) {
        if (productEntity == null)
            return null;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productEntity.getId());
        productDTO.setTitle(productEntity.getTitle());
        productDTO.setDescription(productEntity.getDescription());
        productDTO.setCategory(productEntity.getCategory());
        productDTO.setImage(productEntity.getImage());
        productDTO.setPrice(productEntity.getPrice());
        productDTO.setRating(productEntity.getRating());
        return productDTO;
    }
}
