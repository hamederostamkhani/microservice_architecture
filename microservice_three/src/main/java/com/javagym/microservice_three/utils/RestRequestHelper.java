package com.javagym.microservice_three.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.javagym.microservice_three.controller.StoreController;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class RestRequestHelper {
    private static final Logger log = LoggerFactory.getLogger(RestRequestHelper.class);

    private OkHttpClient client;

    public RestRequestHelper() {
        client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .build();
    }

    public <T> T getOne(String uid, String url, Headers headers, Class<T> clazz) {
        try {
            log.info("[(uid:{})] [(url:{})]", uid, url);
            Request request = createRequest(url, headers);
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                T data = new ObjectMapper().readValue(Objects.requireNonNull(response.body()).string(), clazz);
                log.info("[(uid:{})] [(status:successful)] [(data:{})]", uid, data);
                return data;
            }
            log.error("[(uid:{})] [(status:unsuccessful)] [(data:{})]", uid, response);
            throw new RestAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "Rest API Call Failed", null);
        } catch (Exception e) {
            log.error("[(uid:{})] [(status:unsuccessful)] [(data:{})]", uid, e.getMessage());
            throw new RestAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "Rest API Call Failed", null);
        }
    }

    public <T> List<T> getAll(String uid, String url, Headers headers) {
        try {
            log.info("[(uid:{})] [(url:{})]", uid, url);
            Request request = createRequest(url, headers);
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                Type listType = new TypeToken<List<T>>() {}.getType();
                List<T> data = new Gson().fromJson(Objects.requireNonNull(response.body()).string(), listType);
                log.info("[(uid:{})] [(status:successful)] [(data:{})]", uid, data);
                return data;
            }
            log.error("[(uid:{})] [(status:unsuccessful)] [(data:{})]", uid, response);
            throw new RestAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "Rest API Call Failed", null);
        } catch (Exception e) {
            log.error("[(uid:{})] [(status:unsuccessful)] [(data:{})]", uid, e.getMessage());
            throw new RestAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "Rest API Call Failed", null);
        }
    }

    private Request createRequest(String url, Headers headers) {
        if (url == null) throw new RestAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "Rest API URL Is Not Valid", null);
        if (headers != null)
            return new Request.Builder().url(url).headers(headers).build();
        return new Request.Builder().url(url).build();
    }
}
