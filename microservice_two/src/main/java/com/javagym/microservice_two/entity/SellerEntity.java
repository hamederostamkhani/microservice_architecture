package com.javagym.microservice_two.entity;

import lombok.Data;

@Data
public class SellerEntity {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String image;
}
