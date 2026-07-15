package com.javagym.api_gateway.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SellerProductIds {
//    @NotBlank(message = "Please Send productId")
//    @Pattern(regexp = "/^(?=[a-f\\d]{24}$)(\\d+[a-f]|[a-f]+\\d)/i", message = "Please Send ProductId in Correct Format")
    private String productId;
//    @NotBlank(message = "Please Send userId")
    private Long userId;
}
