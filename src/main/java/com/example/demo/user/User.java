package com.example.demo.user;

import com.example.demo.board.Board;
import com.example.demo.comment.Comment;
import com.example.demo.post.Post;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column
    @CreationTimestamp
    private LocalDateTime createDate = LocalDateTime.now();

    @Column
    @UpdateTimestamp
    private LocalDateTime breakDate = LocalDateTime.now();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<Board> boards;
}