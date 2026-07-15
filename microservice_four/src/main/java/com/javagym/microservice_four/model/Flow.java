package com.javagym.microservice_four.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flow {
    private String uid;
    private List<Node> nodes = new ArrayList<>();

    public static Flow map(HashMap<String, String> event) {
        Flow data = new Gson().fromJson(event.get("flowInfo"), Flow.class);
        return data;
    }

    @Data
    public static class Node {
        private String time;
        private String project;
        private String methodName;
        private String flowStart;
        private String flowEnd;
        private String start;
        private String end;
        private String status;
        private String data;
        private String url;
    }
}
