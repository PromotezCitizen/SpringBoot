package com.example.demo.board;

import com.example.demo.post.PostResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardResponse {
    private String name;
    private String description;
    private PostResponse data;

    public BoardResponse(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
