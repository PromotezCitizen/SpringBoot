package com.example.demo.comment;

import com.example.demo.post.Post;
import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300)
    private String comment;

    @CreationTimestamp
    private LocalDateTime createTime;
    @CreationTimestamp
    @UpdateTimestamp
    private LocalDateTime modifyTime;

    @ColumnDefault("0")
    private Integer like;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
}
