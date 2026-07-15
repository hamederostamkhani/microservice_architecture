package com.javagym.api_gateway.controller;

import com.javagym.api_gateway.entity.ProductsInfo;
import com.javagym.api_gateway.entity.SellersInfo;
import com.javagym.api_gateway.exception.MSNotFound;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("product")
public class ProductGatewayController {
    private static final Logger log = LoggerFactory.getLogger(ProductGatewayController.class);
    private final String MS2 = "ms2";
    private final String MS3 = "ms3";

    private WebClient webClient;
    private DiscoveryClient discoveryClient;

    public ProductGatewayController(WebClient webClient, DiscoveryClient discoveryClient) {
        this.webClient = webClient;
        this.discoveryClient = discoveryClient;
    }

    @PostMapping
    Mono<String> addProduct(@RequestBody ProductsInfo.Product product) {
        String uid = UUID.randomUUID().toString();
        log.info("[(flowStart:AddProduct)] [(uid:{})]", uid);

        URI ms2URI = discoveryClient.getInstances(MS2)
                .stream().map(ServiceInstance::getUri)
                .findFirst().orElseThrow(MSNotFound::new)
                .resolve("/product/" + uid);

        Mono<String> registeredSellerId = this.webClient.post().uri(ms2URI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(product), ProductsInfo.Product.class)
                .retrieve().bodyToMono(String.class);
        log.info("[(flowStart:AddProduct)] [(uid:{})] [(date:{})]", uid, registeredSellerId);
        return registeredSellerId;
    }

    @GetMapping
    Mono<ProductsInfo> getProductInfo() {
        String uid = UUID.randomUUID().toString();
        log.info("[(flowStart:ProductsInfo)] [(uid:{})]", uid);

        URI ms2URI = discoveryClient.getInstances(MS2)
                .stream().map(ServiceInstance::getUri)
                .findFirst().orElseThrow(MSNotFound::new)
                .resolve("/product/" + uid);
        URI ms3URI = discoveryClient.getInstances(MS3)
                .stream().map(ServiceInstance::getUri)
                .findFirst().orElseThrow(MSNotFound::new)
                .resolve("/product/" + uid);


        Mono<ProductsInfo> productsInfoMono = this.webClient.get().uri(ms2URI).retrieve()
                .bodyToFlux(ProductsInfo.Product.class).collectList()
                .zipWhen(
                        sellers -> this.webClient.get().uri(ms3URI).retrieve()
                                .bodyToFlux(ProductsInfo.Product.class).collectList(),
                        (ms2Products, ms3Products) -> {
                            ProductsInfo productsInfo = new ProductsInfo();
                            productsInfo.setUid(uid);
                            productsInfo.setRegisteredProducts(ms2Products);
                            productsInfo.setUnregisteredProducts(ms3Products);
                            log.info("[(flowEnd:ProductsInfo)] [(uid:{})] [(data:{})]", uid, productsInfo);
                            return productsInfo;
                        }
                );
        return productsInfoMono;
    }
}
