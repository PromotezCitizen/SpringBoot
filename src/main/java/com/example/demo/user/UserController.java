package com.example.demo.user;

import com.example.demo.post.Post;
import com.example.demo.post.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> res = getUserDetails(userService.getAll());
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") Long id) {
        UserResponse res = getUserDetail(userService.findById(id));
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("")
    public void postUser(@RequestBody UserRequest User) {
        userService.push(User);
    }

    @PutMapping("/{id}")
    public void putUser(@PathVariable("id") Long id, @RequestBody UserRequest User) {
        userService.modify(id, User);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    private UserResponse getUserDetail(User t) {
        UserResponse res = new UserResponse();
        res.setName(t.getName());
        res.setPosts(getUserWritingDetail(t.getPosts()));
        return res;
    }

    private List<UserResponse> getUserDetails(List<User> t) {
        List<UserResponse> res = new ArrayList<>();
        t.forEach(a -> res.add(new UserResponse(a.getName(), getUserWritingDetail(a.getPosts()))));
        return res;
    }

    private List<PostResponse> getUserWritingDetail(List<Post> p) {
        List<PostResponse> res = new ArrayList<>();
        p.forEach(a -> res.add(new PostResponse(a.getId(), a.getTitle(), a.getContent())));
        return res;
    }
}
