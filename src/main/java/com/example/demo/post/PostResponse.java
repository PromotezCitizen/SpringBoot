package com.example.demo.post;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostResponse {
    public Integer counts;
    public List<ResponseData> data;

    public PostResponse() {
        data = new ArrayList<>();
    }

    public PostResponse(Integer counts) {
        this.counts = counts;
        data = new ArrayList<>();
    }

    @AllArgsConstructor
    public static class ResponseData {
        public Long id;
        public String title;
        public String content;
    }
}
