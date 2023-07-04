package com.example.demo.user;

import com.example.demo.post.Post;
import com.example.demo.post.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public String hellowWorld(){
        return "hello, world";
    }
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> res = getUserDetails(userService.getAll());
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") Long id,
                                                @RequestParam(required = false, value = "page") Integer pageNo,
                                                Pageable pageable) {
        UserResponse res = getUserDetail(userService.findById(id));
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("")
    public ResponseEntity<User> postUser(@RequestBody UserRequest uReq) {
        if (userService.findByName(uReq.getName()) != null) {
            return ResponseEntity.status(HttpStatus.IM_USED).build();
        }
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

    private List<PostResponse.ResponseData> getUserWritingDetail(List<Post> p) {
        List<PostResponse.ResponseData> res = new ArrayList<>();
        p.forEach(a -> res.add(new PostResponse.ResponseData(a.getId(), a.getTitle(), a.getContent())));
        return res;
    }
}
