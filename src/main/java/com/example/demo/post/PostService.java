package com.example.demo.post;

import com.example.demo.board.Board;
import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public Post push(Post post) {
        return postRepository.save(post);
    }

    public Post getPost(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        return postOptional.orElse(null);
    }

    public PostResponse getPage(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 10);
        Page<Post> page = postRepository.findAllByOrderByIdDesc(pageable);

        PostResponse res = makePostResponse(page);
        return res;
    }

    public PostResponse getUsersPage(Integer pageNo, User user) {
        Pageable pageable = PageRequest.of(pageNo, 10);
        Page<Post> page = postRepository.findAllByUserOrderByIdDesc(pageable, user);

        PostResponse res = makePostResponse(page);
        return res;
    }

    public PostResponse getBoardsPage(Integer pageNo, Board board) {
        Pageable pageable = PageRequest.of(pageNo, 10);
        Page<Post> page = postRepository.findAllByBoardOrderByIdDesc(pageable, board);

        PostResponse res = makePostResponse(page);
        return res;
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    private PostResponse makePostResponse(Page<Post> page) {
        PostResponse res = new PostResponse(page.getTotalPages());

        page.getContent().forEach( post -> {
            Boolean modified = post.getCreateTime() == post.getModifyTime() ? false : true;
            res.data.add(new PostResponse.ResponseData(
                            post.getId(),
                            post.getTitle(),
                            post.getContent(),
                            post.getLike(),
                            post.getCreateTime(),
                            modified
                    )
            );
        });
        return res;
    }
}
