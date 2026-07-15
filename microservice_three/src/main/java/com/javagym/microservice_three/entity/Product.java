package com.javagym.microservice_three.entity;

import lombok.Data;

@Data
public class Product {
    private String id;
    private String title;
    private Double price;
    private String description;
    private String image;
    private String category;
    private Rating rating;
}
