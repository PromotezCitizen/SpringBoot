package com.example.demo.post;

import com.example.demo.board.Board;
import com.example.demo.comment.Comment;
import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String title;
    private String content;

    @ColumnDefault("0")
    private Integer likes;

    @Column
    @CreationTimestamp
    private LocalDateTime createTime = LocalDateTime.now();

    @Column
    @UpdateTimestamp
    private LocalDateTime modifiedTime = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;
}
