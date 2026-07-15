package com.javagym.microservice_two.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javagym.microservice_two.config.RabbitMQConfig;
import com.javagym.microservice_two.entity.ProductEntity;
import com.javagym.microservice_two.entity.RabbitMessage;
import com.javagym.microservice_two.entity.SellerEntity;
import com.javagym.microservice_two.entity.SellerProduct;
import com.javagym.microservice_two.repository.ProductRepository;
import com.javagym.microservice_two.repository.SellerProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RabbitMessageConsumerService {
    private static final Logger log = LoggerFactory.getLogger(RabbitMessageConsumerService.class);

    private final ProductRepository productRepository;
    private final SellerProductRepository sellerProductRepository;
    private final RabbitMessageProducerService rabbitMessageProducerService;

    public RabbitMessageConsumerService(ProductRepository productRepository,
                                        SellerProductRepository sellerProductRepository,
                                        RabbitMessageProducerService rabbitMessageProducerService) {
        this.productRepository = productRepository;
        this.sellerProductRepository = sellerProductRepository;
        this.rabbitMessageProducerService = rabbitMessageProducerService;
    }

    @Async
    @RabbitListener(queues = RabbitMQConfig.MS1_QUEUE)
    public void ms1QueueListener(String data) {
        try {
            System.out.println(data);
            RabbitMessage<SellerEntity, String> receivedMessage = new ObjectMapper().readValue(data, RabbitMessage.class);
            log.info("[(start:AddProductToSeller)] [(queue:{})] [(uid:{})] [(data:{})]", RabbitMQConfig.MS1_QUEUE,
                    receivedMessage.getUid(), receivedMessage);

            ProductEntity productEntity = productRepository.findById(receivedMessage.getSecondData()).block();
            if (productEntity == null) {
                RabbitMessage<String, String> rabbitMessage = new RabbitMessage<>();
                rabbitMessage.setFirstData("product not found");
                rabbitMessage.setUid(receivedMessage.getUid());
                rabbitMessageProducerService.sendMessageToQueue(rabbitMessage);
            } else {
                SellerProduct newSP = new SellerProduct();
                Long id = receivedMessage.getFirstData().getId();
                System.out.println(id);
                newSP.setSellerId(id);
                newSP.setProduct(productEntity);
                newSP = sellerProductRepository.save(newSP).block();
                log.info("[(end:AddProductToSeller)] [(uid:{})] [(data:{})]", receivedMessage.getUid(), newSP);
            }


//            productRepository.findById(receivedMessage.getSecondData()).flatMap(productEntity -> {
//                sellerProductRepository.findBySeller(receivedMessage.getFirstData()).flatMap(sellerProduct -> {
//                    sellerProduct.add(productEntity);
//                    sellerProductRepository.save(sellerProduct)
//                            .doOnNext(sp -> log.info("[(end:AddProductToSeller)] [(uid:{})] [(data:{})]", receivedMessage.getUid(), sp));
//
//                    return Mono.empty();
//                }).switchIfEmpty(Mono.defer(() -> {
//                    SellerProduct sellerProduct = new SellerProduct();
//                    sellerProduct.setSeller(receivedMessage.getFirstData());
//                    sellerProduct.add(productEntity);
//                    sellerProductRepository.save(sellerProduct)
//                            .doOnNext(sp -> log.info("[(end:AddProductToSeller)] [(uid:{})] [(data:{})]", receivedMessage.getUid(), sp));
//                    return Mono.empty();
//                }));
//                RabbitMessage<String, String> rabbitMessage = new RabbitMessage<>();
//                rabbitMessage.setFirstData("product add to seller");
//                rabbitMessage.setUid(receivedMessage.getUid());
//                rabbitMessageProducerService.sendMessageToQueue(rabbitMessage);
//                return Mono.empty();
//            }).switchIfEmpty(Mono.defer(() -> {
//                RabbitMessage<String, String> rabbitMessage = new RabbitMessage<>();
//                rabbitMessage.setFirstData("product not found");
//                rabbitMessage.setUid(receivedMessage.getUid());
//                rabbitMessageProducerService.sendMessageToQueue(rabbitMessage);
//                return Mono.empty();
//            }));

        } catch (IOException e) {
            log.error("[(errorData:{})] [(errorMessage:{})]", data, e.getMessage());
        }
    }
}