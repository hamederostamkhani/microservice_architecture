package com.javagym.microservice_three.entity;

import lombok.Data;
import java.util.List;

@Data
public class FakeUsers {
    private Integer page;
    private Integer per_page;
    private Integer total;
    private Integer total_pages;
    private List<UserInfo> data;
    private Support support;

    @Data
    public static class UserInfo {
        private Long id;
        private String first_name;
        private String last_name;
        private String email;
        private String avatar;
    }

    @Data
    public static class Support {
        private String url;
        private String text;
    }
}
