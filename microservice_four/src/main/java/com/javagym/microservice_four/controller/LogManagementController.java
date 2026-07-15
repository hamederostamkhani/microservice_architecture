package com.javagym.microservice_four.controller;

import com.javagym.microservice_four.model.CollectionInfo;
import com.javagym.microservice_four.model.DateRangeFilter;
import com.javagym.microservice_four.model.Flow;
import com.javagym.microservice_four.repository.SplunkRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("logManagement")
public class LogManagementController {

    private SplunkRepository splunkRepository;

    public LogManagementController(SplunkRepository splunkRepository) {
        this.splunkRepository = splunkRepository;
    }

    @PostMapping("getFlows")
    public Mono<CollectionInfo<Flow>> getFlows(@RequestBody DateRangeFilter rangeFilter) {
        return splunkRepository.getFlowInfo(rangeFilter);
    }
}
