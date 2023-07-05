package com.example.demo.board;

import com.example.demo.post.Post;
import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @CreationTimestamp
    private LocalDateTime createTime;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Post> posts;

    private User user;
}
