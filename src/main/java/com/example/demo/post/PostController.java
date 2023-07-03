package com.example.demo.post;

import com.example.demo.user.User;
import com.example.demo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/posts")
public class PostController {
    private final UserService userService;
    private final PostService postService;


    @GetMapping("/{id}")
    public Post getPost(@PathVariable Long id) {
        Post post = postService.getPost(id);
        return post;
    }

    @PostMapping("")
    public Post postPost(@RequestBody PostRequest req, @RequestHeader String token) {
        User user = userService.findByName(token);
        Post post = new Post();
        post.setUser(user);
        post.setTitle(req.getTitle());
        post.setContent(req.getContent());
        return postService.push(post);
    }

}
