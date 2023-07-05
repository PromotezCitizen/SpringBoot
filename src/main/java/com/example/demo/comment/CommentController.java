package com.example.demo.comment;

import com.example.demo.board.Board;
import com.example.demo.post.Post;
import com.example.demo.post.PostService;
import com.example.demo.user.User;
import com.example.demo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService;
    // comment 단독으로 get 하는 경우는 없으니 GetMapping 제외
    @PostMapping("/{post_id}")
    public ResponseEntity<Comment> postComment(@PathVariable("post_id") Long post_id,
                                               @RequestBody CommentRequest req,
                                               @RequestHeader("token") String token) {
        String nickname = token;
        User user = userService.findByName(nickname);
        Post post = postService.getPost(post_id);
        Comment comment = new Comment();
        comment.setComment(req.getComment());
        comment.setUser(user);
        comment.setPost(post);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.push(comment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> modifyComment(@PathVariable("id") Long id,
                                                 @RequestBody CommentRequest req,
                                                 @RequestHeader("token") String token) {
        String nickname = token;
        Comment comment = commentService.getComment(id);
        if (comment == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        if (!comment.getUser().getName().equals(nickname))
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();

        comment.setComment(req.getComment());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.push(comment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable Long id,
                                                @RequestHeader("token") String token) {

        String nickname = token;
        Comment comment = commentService.getComment(id);
        HttpStatus status = checkPossibility(comment, nickname);
        if (status.equals(HttpStatus.BAD_REQUEST))
            return ResponseEntity
                    .status(status)
                    .build();

        commentService.removeComment(comment);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    private HttpStatus checkPossibility(Comment comment, String nickname) {
        if (comment == null)
            return HttpStatus.BAD_REQUEST;
        else if (!comment.getUser().getName().equals(nickname))
            return HttpStatus.BAD_REQUEST;
        return HttpStatus.OK;
    }
}
