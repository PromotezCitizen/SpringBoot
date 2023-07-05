package com.example.demo.post;

import com.example.demo.board.Board;
import com.example.demo.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByOrderByIdDesc(Pageable pageable);

    Page<Post> findAllByUserOrderByIdDesc(Pageable pageable, User user);

    Page<Post> findAllByBoardOrderByIdDesc(Pageable pageable, Board board);
}
