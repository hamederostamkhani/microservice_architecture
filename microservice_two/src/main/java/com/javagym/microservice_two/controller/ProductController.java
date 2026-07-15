package com.javagym.microservice_two.controller;

import com.javagym.microservice_two.entity.dto.ProductDTO;
import com.javagym.microservice_two.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("product")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("{uid}")
    public Mono<String> addProduct(@RequestBody ProductDTO productDTO, @PathVariable String uid) {
        log.info("[(start:RegisterProduct)] [(uid:{})]", uid);
        Mono<String> productId = productService.addProduct(productDTO);
        log.info("[(end:RegisterProduct)] [(uid:{})]", uid);
        return productId;
    }

    @GetMapping("{uid}")
    public Flux<ProductDTO> getAll(@PathVariable String uid) {
        log.info("[(start:AllRegisteredProducts)] [(uid:{})]", uid);
        Flux<ProductDTO> all = productService.getAll();
        log.info("[(end:AllRegisteredProducts)] [(uid:{})] [(data:{})]", uid, all);
        return all;
    }
}
