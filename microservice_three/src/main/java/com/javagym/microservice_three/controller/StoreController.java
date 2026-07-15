package com.javagym.microservice_three.controller;

import com.javagym.microservice_three.entity.FakeUsers;
import com.javagym.microservice_three.entity.Product;
import com.javagym.microservice_three.entity.Seller;
import com.javagym.microservice_three.utils.RestRequestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class StoreController {
    private static final Logger log = LoggerFactory.getLogger(StoreController.class);

    private RestRequestHelper restRequestHelper;
    private List<Seller> sellers = new ArrayList<>();
    private List<Product> products = new ArrayList<>();

    public StoreController(RestRequestHelper restRequestHelper) {
        this.restRequestHelper = restRequestHelper;
    }

    @GetMapping("seller/{uid}")
    public ResponseEntity<?> getSellers(@PathVariable String uid) {
        log.info("[(start:AvailableSellers)] [(uid:{})]", uid);
        if (sellers.isEmpty()) {
            String url = "https://reqres.in/api/users?page=1";
            FakeUsers fakeUsers1 = restRequestHelper.getOne(uid, url, null, FakeUsers.class);
            url = "https://reqres.in/api/users?page=2";
            FakeUsers fakeUsers2 = restRequestHelper.getOne(uid, url, null, FakeUsers.class);
            sellers = Stream.concat(fakeUsers1.getData().stream(), fakeUsers2.getData().stream())
                    .map(this::map).collect(Collectors.toList());
        }
        log.info("[(end:AvailableSellers)] [(uid:{})] [(data:{})]", uid, sellers);
        return ResponseEntity.ok(sellers);
    }


    @GetMapping("product/{uid}")
    public ResponseEntity<?> getProducts(@PathVariable String uid) {
        log.info("[(start:AvailableProducts)] [(uid:{})]", uid);
        if (products.isEmpty()) {
            String url = "https://fakestoreapi.com/products";
            products = restRequestHelper.getAll(uid, url, null);
        }
        log.info("[(end:AvailableProducts)] [(uid:{})] [(data:{})]", uid, products);
        return ResponseEntity.ok(products);
    }


    private Seller map(FakeUsers.UserInfo userInfo) {
        if (userInfo == null)
            return null;
        Seller seller = new Seller();
        seller.setFirstName(userInfo.getFirst_name());
        seller.setLastName(userInfo.getLast_name());
        seller.setEmail(userInfo.getEmail());
        seller.setImage(userInfo.getAvatar());
        return seller;
    }
}
