package com.javagym.api_gateway.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FlowsInfo {
    private Integer totalItems;
    private List<Flow> response;

    @Data
    public static class Flow {
        private String uid;
        private List<Node> nodes = new ArrayList<>();

        @Data
        public static class Node {
            private String time;
            private String project;
            private String methodName;
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private String flowStart;
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private String flowEnd;
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private String start;
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private String end;
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private String status;
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private String data;
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private String url;
        }
    }
}
