package com.javagym.microservice_one.utils;

import java.util.UUID;

public class CommonUtils {

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
