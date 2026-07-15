package com.javagym.api_gateway.controller;

import com.javagym.api_gateway.entity.DateRangeFilter;
import com.javagym.api_gateway.entity.FlowsInfo;
import com.javagym.api_gateway.exception.MSNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("logManagement")
public class LogManagementGatewayController {
    private static final Logger log = LoggerFactory.getLogger(SellerGatewayController.class);
    private final String MS4 = "ms4";

    private WebClient webClient;
    private DiscoveryClient discoveryClient;

    public LogManagementGatewayController(WebClient webClient, DiscoveryClient discoveryClient) {
        this.webClient = webClient;
        this.discoveryClient = discoveryClient;
    }

    @PostMapping
    Mono<FlowsInfo> getFlows(@RequestBody @Valid DateRangeFilter rangeFilter) {
//        String uid = UUID.randomUUID().toString();
//        log.info("[(flowStart:getFlows)] [(uid:{})]", uid);

        URI ms4URI = discoveryClient.getInstances(MS4)
                .stream().map(ServiceInstance::getUri)
                .findFirst().orElseThrow(MSNotFound::new)
                .resolve("/logManagement/getFlows");

        return this.webClient.post().uri(ms4URI)
                .body(Mono.just(rangeFilter), DateRangeFilter.class)
                .retrieve().bodyToMono(FlowsInfo.class);
//                .doOnSuccess(flows -> log.info("[(flowEnd:getFlows)] [(uid:{})] [(data:{})]", uid, flows))
//                .doOnError(throwable -> log.info("[(flowEnd:getFlows)] [(uid:{})] [(data:{})]", uid, throwable.toString()));
    }

}
