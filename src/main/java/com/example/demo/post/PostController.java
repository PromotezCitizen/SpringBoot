package com.example.demo.post;

import com.example.demo.user.User;
import com.example.demo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {
    private final UserService userService;
    private final PostService postService;

    @GetMapping("")
    public ResponseEntity<PostResponse> getPosts(@RequestParam("pages") Integer pageNo) {
        System.out.println(pageNo);
        PostResponse page = postService.getPage(pageNo);
        if (page == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse.ResponseData> getPost(@PathVariable Long id) {
        Post post = postService.getPost(id);
        if (post == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        PostResponse.ResponseData res = new PostResponse.ResponseData(post.getId(), post.getTitle(), post.getContent());
        System.out.println(res.title);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("")
    public ResponseEntity<PostResponse.ResponseData> postPost(@RequestBody PostRequest req, @RequestHeader String token) {
        User user = userService.findByName(token);
        Post post = new Post();
        post.setUser(user);
        post.setTitle(req.getTitle());
        post.setContent(req.getContent());
        post = postService.push(post);

        PostResponse.ResponseData res = new PostResponse.ResponseData(post.getId(), req.getTitle(), req.getContent());

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}
