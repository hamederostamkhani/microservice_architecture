package com.javagym.microservice_one.controller;

import com.javagym.microservice_one.entity.SellerDTO;
import com.javagym.microservice_one.entity.SellerProductIds;
import com.javagym.microservice_one.service.RabbitMessageConsumerService;
import com.javagym.microservice_one.service.SellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


@RestController
@RequestMapping("seller")
public class SellerController {
    private static final Logger log = LoggerFactory.getLogger(SellerController.class);

    private SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @PostMapping("{uid}")
    public Mono<Long> addSeller(@RequestBody SellerDTO sellerDTO, @PathVariable String uid) {
        log.info("[(start:RegisterSeller)] [(uid:{})] [(data:{})]", uid, sellerDTO);
        return sellerService.addSeller(sellerDTO).doOnNext(id ->
                log.info("[(end:RegisterSeller)] [(uid:{})] [(data:{})]", uid, id));
    }

    @PutMapping("{uid}")
    public Mono<String> addProduct(@RequestBody @Valid SellerProductIds sellerProductIds, @PathVariable String uid) {
        log.info("[(start:AddProductToSeller)] [(uid:{})]", uid);
        return sellerService.checkAndSend(uid, sellerProductIds).doOnNext(message ->
                log.info("[(end:AddProductToSeller)] [(uid:{})] [(data:{})]", uid, message));
    }

    @GetMapping("{uid}")
    public Flux<SellerDTO> getAll(@PathVariable String uid) {
        log.info("[(start:AllRegisteredSellers)] [(uid:{})]", uid);
        Flux<SellerDTO> all = sellerService.getAll();
        log.info("[(end:AllRegisteredSellers)] [(uid:{})]", uid);
        return all;
    }

}
