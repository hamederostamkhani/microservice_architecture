package com.javagym.microservice_two.entity;

import lombok.Data;

@Data
public class RabbitMessage<T, D> {
    private String uid;
    private T firstData;
    private D secondData;
}
