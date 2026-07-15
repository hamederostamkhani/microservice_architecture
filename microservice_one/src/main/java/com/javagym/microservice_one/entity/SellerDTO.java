package com.javagym.microservice_one.entity;

import lombok.Data;

@Data
public class SellerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String image;
}
