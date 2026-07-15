package com.javagym.api_gateway.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellersInfo {
    private String uid;
    private List<Seller> registeredSellers;
    private List<Seller> unregisteredSellers;

    @Data
    public static class Seller {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String image;
    }
}
