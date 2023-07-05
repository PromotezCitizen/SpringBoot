package com.example.demo.user;

import com.example.demo.comment.CommentResponse;
import com.example.demo.comment.CommentService;
import com.example.demo.post.PostResponse;
import com.example.demo.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@RequestHeader("token") String token) {
        String nickname = token;
        User user = userService.findByName(nickname);
        if (user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        UserResponse res = new UserResponse();
        Boolean isBanned = LocalDateTime.now().isBefore(user.getBreakDate());
        res.setName(nickname);
        res.setSignedDate(user.getCreateDate());
        res.setIsBanned(isBanned);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getUsersPosts(@RequestParam(value = "pages") Integer pageNo,
                                                      @RequestHeader("token") String token) {
        String nickname = token;
        User user = userService.findByName(nickname);
        PostResponse res = postService.getUsersPage(pageNo, user);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/comments")
    public ResponseEntity<CommentResponse> getUsersComments(@RequestParam(value = "pages") Integer pageNo,
                                                            @RequestHeader("token") String token) {
        String nickname = token;
        User user = userService.findByName(nickname);
        CommentResponse res = commentService.getUsersPage(pageNo, user);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/dupChk")
    public ResponseEntity<Object> duplicateNicknameCheck(@RequestParam("nickname") String nickname) {
        User user = userService.findByName(nickname);
        if (user != null)
            return ResponseEntity.status(HttpStatus.IM_USED).build();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("")
    public ResponseEntity<User> postUser(@RequestBody UserRequest uReq) {
        if (userService.findByName(uReq.getName()) != null)
            return ResponseEntity.status(HttpStatus.IM_USED).build();

        User user = userService.push(uReq);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{id}")
    public void putUser(@PathVariable("id") Long id, @RequestBody UserRequest User) {
        userService.modify(id, User);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
    }


    /*
    // ================== 이 영역은 곧 지울 영역입니다. ==================
    private UserResponse getUserDetail(User t) {
        if (t == null)
            return null;
        UserResponse res = new UserResponse();
        res.setName(t.getName());
        res.setPosts(getUserWritingDetail(t.getPosts()));
        return res;
    }

    private List<PostResponse.ResponseData> getUserWritingDetail(List<Post> posts) {
        List<PostResponse.ResponseData> res = new ArrayList<>();
        posts.forEach(post -> {
            Boolean modified = post.getCreateTime() == post.getCreateTime() ? false : true;
            res.add(new PostResponse.ResponseData(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getLike(),
                    post.getModifyTime(),
                    modified
            ));
        });
        return res;
    }
    // ================== = ==== == === ======= ==================
    */
}
