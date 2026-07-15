package com.javagym.microservice_two.entity.dto;

import com.javagym.microservice_two.entity.Rating;
import lombok.Data;

@Data
public class ProductDTO {
    private String id;
    private String title;
    private Double price;
    private String description;
    private String image;
    private String category;
    private Rating rating;
}
