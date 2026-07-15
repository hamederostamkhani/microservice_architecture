package com.javagym.microservice_one.service;

import com.javagym.microservice_one.entity.RabbitMessage;
import com.javagym.microservice_one.entity.SellerDTO;
import com.javagym.microservice_one.entity.SellerEntity;
import com.javagym.microservice_one.entity.SellerProductIds;
import com.javagym.microservice_one.repository.SellerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class SellerService {

    private SellerRepository sellerRepository;
    private RabbitMessageProducerService rabbitMessageProducerService;

    public SellerService(SellerRepository sellerRepository, RabbitMessageProducerService rabbitMessageProducerService) {
        this.sellerRepository = sellerRepository;
        this.rabbitMessageProducerService = rabbitMessageProducerService;
    }

    public Mono<Long> addSeller(SellerDTO sellerDTO) {
        return sellerRepository.save(map(sellerDTO)).map(SellerEntity::getId);
    }

    public Flux<SellerDTO> getAll() {
        return sellerRepository.findAll().map(this::map);
    }

    public Mono<String> checkAndSend(String uid, SellerProductIds sellerProductIds) {
        return sellerRepository.findById(sellerProductIds.getUserId()).flatMap(sellerEntity -> {
            RabbitMessage<SellerDTO, String> rabbitMessage = new RabbitMessage<>();
            rabbitMessage.setUid(uid);
            rabbitMessage.setFirstData(map(sellerEntity));
            rabbitMessage.setSecondData(sellerProductIds.getProductId());
            rabbitMessageProducerService.sendMessageToQueue(rabbitMessage);
            return Mono.just("Message Sent");
        }).switchIfEmpty(Mono.just("This User Not Exist"));
    }

    private SellerEntity map(SellerDTO sellerDTO) {
        if (sellerDTO == null)
            return null;
        SellerEntity sellerEntity = new SellerEntity();
        sellerEntity.setFirstName(sellerDTO.getFirstName());
        sellerEntity.setLastName(sellerDTO.getLastName());
        sellerEntity.setEmail(sellerDTO.getEmail());
        sellerEntity.setImage(sellerDTO.getImage());
        sellerEntity.setValidFrom(LocalDateTime.now());
        return sellerEntity;
    }

    private SellerDTO map(SellerEntity sellerEntity) {
        if (sellerEntity == null)
            return null;
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setId(sellerEntity.getId());
        sellerDTO.setFirstName(sellerEntity.getFirstName());
        sellerDTO.setLastName(sellerEntity.getLastName());
        sellerDTO.setEmail(sellerEntity.getEmail());
        sellerDTO.setImage(sellerEntity.getImage());
        return sellerDTO;
    }
}
