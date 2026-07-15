package com.javagym.microservice_two.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class SellerProduct {
    @Id
    private String id;
    private Long sellerId;
    private ProductEntity product;
}
