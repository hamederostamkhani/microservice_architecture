package com.javagym.microservice_four.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.InputStream;

@Data
@AllArgsConstructor
public class SplunkResult {
    private InputStream result;
    private Integer resultCount;

    public static SplunkResult create(InputStream result, Integer resultCount) {
        return new SplunkResult(result, resultCount);
    }
}
