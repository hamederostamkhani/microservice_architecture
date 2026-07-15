package com.javagym.microservice_four.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CollectionInfo<T> {
    private Integer totalItems;
    private List<T> response;
}
