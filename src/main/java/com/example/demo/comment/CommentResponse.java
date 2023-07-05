package com.example.demo.comment;

import com.example.demo.post.PostInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class CommentResponse {
    public Integer commentCount;
    public List<ResponseData> data;

    public CommentResponse() {
        data = new ArrayList<ResponseData>();
    }
    public CommentResponse(Integer commentCount) {
        this.commentCount = commentCount;
        data = new ArrayList<ResponseData>();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class ResponseData {
        private Long id;
        private String commnet;
        private String nickname;
        private Integer like;
        private LocalDateTime timestamp;
        private Boolean modified;
        private PostInfo postInfo;
    }
}
