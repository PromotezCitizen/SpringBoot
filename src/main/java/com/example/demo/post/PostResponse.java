package com.example.demo.post;

import lombok.*;

import java.time.LocalDateTime;
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
        this();
        this.counts = counts;
    }

    @AllArgsConstructor
    public static class ResponseData {
        public Long id;
        public String title;
        public String content;
        public Integer like;
        public LocalDateTime time;
        public Boolean modified;
    }
}
