package com.javagym.api_gateway.controller;

import com.javagym.api_gateway.entity.ProductsInfo;
import com.javagym.api_gateway.entity.SellerProductIds;
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

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("seller")
public class SellerGatewayController {
    private static final Logger log = LoggerFactory.getLogger(SellerGatewayController.class);
    private final String MS1 = "ms1";
    private final String MS3 = "ms3";

    private WebClient webClient;
    private DiscoveryClient discoveryClient;

    public SellerGatewayController(WebClient webClient, DiscoveryClient discoveryClient) {
        this.webClient = webClient;
        this.discoveryClient = discoveryClient;
    }

    @PostMapping
    Mono<Long> addSeller(@RequestBody SellersInfo.Seller seller) {
        String uid = UUID.randomUUID().toString();
        log.info("[(flowStart:AddSeller)] [(uid:{})]", uid);

        URI ms1URI = discoveryClient.getInstances(MS1)
                .stream().map(ServiceInstance::getUri)
                .findFirst().orElseThrow(MSNotFound::new)
                .resolve("/seller/" + uid);

        Mono<Long> registeredSellerId = this.webClient.post().uri(ms1URI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(seller), SellersInfo.Seller.class)
                .retrieve().bodyToMono(Long.class);
        log.info("[(flowStart:AddSeller)] [(uid:{})] [(date:{})]", uid, registeredSellerId);
        return registeredSellerId;
    }

    @PutMapping
    Mono<String> addProduct(@RequestBody SellerProductIds sellerProductIds) {
        String uid = UUID.randomUUID().toString();
        log.info("[(flowStart:AddProductToSeller)] [(uid:{})]", uid);

        URI ms1URI = discoveryClient.getInstances(MS1)
                .stream().map(ServiceInstance::getUri)
                .findFirst().orElseThrow(MSNotFound::new)
                .resolve("/seller/" + uid);

        Mono<String> ms1Message = this.webClient.put().uri(ms1URI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(sellerProductIds), SellerProductIds.class)
                .retrieve().bodyToMono(String.class);
        log.info("[(flowStart:AddProductToSeller)] [(uid:{})] [(date:{})]", uid, ms1Message);
        return ms1Message;
    }

    @GetMapping
    Mono<SellersInfo> getSellerInfo() {
        String uid = UUID.randomUUID().toString();
        log.info("[(flowStart:SellersInfo)] [(uid:{})]", uid);

        URI ms1URI = discoveryClient.getInstances(MS1)
                .stream().map(ServiceInstance::getUri)
                .findFirst().orElseThrow(MSNotFound::new)
                .resolve("/seller/" + uid);
        URI ms3URI = discoveryClient.getInstances(MS3)
                .stream().map(ServiceInstance::getUri)
                .findFirst().orElseThrow(MSNotFound::new)
                .resolve("/seller/" + uid);


        Mono<SellersInfo> sellersInfoMono = this.webClient.get().uri(ms1URI).retrieve()
                .bodyToFlux(SellersInfo.Seller.class).collectList()
                .zipWhen(
                        sellers -> this.webClient.get().uri(ms3URI).retrieve()
                                .bodyToFlux(SellersInfo.Seller.class).collectList(),
                        (ms1Sellers, ms3Sellers) -> {
                            SellersInfo sellersInfo = new SellersInfo();
                            sellersInfo.setUid(uid);
                            sellersInfo.setRegisteredSellers(ms1Sellers);
                            sellersInfo.setUnregisteredSellers(ms3Sellers);
                            log.info("[(flowEnd:SellersInfo)] [(uid:{})] [(data:{})]", uid, sellersInfo);
                            return sellersInfo;
                        }
                );
        return sellersInfoMono;
    }
}
