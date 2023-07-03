package com.example.demo.post;

import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String title;
    private String content;

    @ManyToOne
    private User user;
}
