package com.example.demo.post;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostRequest {
    private String title;
    private String content;
}
