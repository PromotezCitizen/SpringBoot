package com.example.demo.post;

import com.example.demo.comment.Comment;
import com.example.demo.user.User;
import com.example.demo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

        LocalDateTime time = post.getModifiedTime() != null ? post.getModifiedTime() : post.getCreateTime();
        Boolean modified = post.getModifiedTime() != null;
        PostResponse.ResponseData res = new PostResponse.ResponseData(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getLikes(),
                time,
                modified
        );
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

        PostResponse.ResponseData res = new PostResponse.ResponseData(
                post.getId(),
                req.getTitle(),
                req.getContent(),
            0,
                post.getCreateTime(),
                false
        );

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse.ResponseData> modifyPost(@RequestParam("id") Long id,
                                                                @RequestBody PostRequest req,
                                                                @RequestHeader String token) {
        String nickname = token;
        Post post = postService.getPost(id);

        HttpStatus status = checkPossibility(post, nickname);
        if (status.equals(HttpStatus.BAD_REQUEST))
            return ResponseEntity
                    .status(status)
                    .build();

        post.setTitle(req.getTitle());
        post.setContent(req.getTitle());
        post = postService.push(post);

        PostResponse.ResponseData res = new PostResponse.ResponseData(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getLikes(),
                post.getModifiedTime(),
                true
        );

        return ResponseEntity.status(status).body(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePost(@RequestParam("id") Long id,
                                             @RequestHeader String token) {
        HttpStatus status;
        try {
            postService.deletePost(id);
            status = HttpStatus.OK;

        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).build();
    }

    private HttpStatus checkPossibility(Post post, String nickname) {
        if (post == null)
            return HttpStatus.BAD_REQUEST;
        else if (!post.getUser().getName().equals(nickname))
            return HttpStatus.BAD_REQUEST;
        return HttpStatus.OK;
    }
}
