package com.javagym.microservice_four.repository;

import com.javagym.microservice_four.config.SplunkObjectPool;
import com.javagym.microservice_four.model.DateRangeFilter;
import com.javagym.microservice_four.model.Flow;
import com.javagym.microservice_four.model.CollectionInfo;
import com.javagym.microservice_four.model.SplunkResult;
import com.splunk.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class SplunkRepository {
    private static final Logger log = LoggerFactory.getLogger(SplunkRepository.class);

    private SplunkObjectPool splunkObjectPool;

    public SplunkRepository(SplunkObjectPool splunkObjectPool) {
        this.splunkObjectPool = splunkObjectPool;
    }

    public Mono<CollectionInfo<Flow>> getFlowInfo(DateRangeFilter rangeFilter) {
        return getListFromSplunk(Flow.class, SplunkQuery.getFlowInfo(rangeFilter));
    }

    private <T> Mono<CollectionInfo<T>> getListFromSplunk(Class<T> clazz, String query) {
        log.info("[(query:{})]", query);
        CollectionInfo<T> collectionInfo = null;
        List<T> response = new ArrayList<>();
        Service service = null;
        try {
            service = splunkObjectPool.borrowObject();
            SplunkResult results = getResults(service, query);
            ResultsReaderXml readerXml = new ResultsReaderXml(results.getResult());
            HashMap<String, String> event;
            while ((event = readerXml.getNextEvent()) != null) {
                T entity = (T) clazz.getMethod("map", HashMap.class).invoke(null, event);
                response.add(entity);
            }
            log.info("[(data:{})]", response);
            collectionInfo = new CollectionInfo<>(results.getResultCount(), response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            splunkObjectPool.returnObject(service);
        }
        return Mono.justOrEmpty(collectionInfo);
    }

    private SplunkResult getResults(Service service, String queryString) {
        JobArgs jobArgs = new JobArgs();
        jobArgs.setExecutionMode(JobArgs.ExecutionMode.NORMAL);
        Job job = service.getJobs().create(queryString, jobArgs);
        while (!job.isDone()) {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return SplunkResult.create(job.getResults(), job.getResultCount());
    }
}
