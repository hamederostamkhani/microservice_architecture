package com.javagym.api_gateway.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsInfo {
    private String uid;
    private List<Product> registeredProducts;
    private List<Product> unregisteredProducts;

    @Data
    public static class Product {
        private String id;
        private String title;
        private Double price;
        private String description;
        private String image;
        private String category;
        private Rating rating;

        @Data
        public static class Rating {
            private Float rate;
            private Integer count;
        }
    }
}
