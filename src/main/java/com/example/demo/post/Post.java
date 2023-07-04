package com.example.demo.post;

import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "user")
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
