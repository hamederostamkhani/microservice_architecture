package com.javagym.microservice_two.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class ProductEntity {
    @Id
    private String id;
    private String title;
    private Double price;
    private String description;
    private String image;
    private String category;
    private Rating rating;
    private Date validFrom;
    private Date validTo;
}
