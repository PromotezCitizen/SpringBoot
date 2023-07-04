package com.example.demo.post;

import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        if (postOptional.isPresent()) {
            return postOptional.get();
        }
        return null;
    }

    public PostResponse getPage(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 10);
        Page<Post> page = postRepository.findAllByOrderByIdDesc(pageable);

        PostResponse res = new PostResponse(page.getTotalPages());
        page.getContent().forEach( post -> res.data.add(new PostResponse.ResponseData(post.getId(), post.getTitle(), post.getContent())));

        return res;
    }
}
