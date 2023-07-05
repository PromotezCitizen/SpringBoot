package com.example.demo.comment;

import com.example.demo.post.Post;
import com.example.demo.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByUserOrderByIdDesc(Pageable pageable, User user);
}
