package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> testOptional = userRepository.findById(id);
        return testOptional.orElse(null);
    }

    public User findByName(String name) {
        Optional<User> testOptional = userRepository.findByName(name);
        return testOptional.orElse(null);
    }

    public void push(UserRequest test) {
        User t = new User();
        t.setName(test.getName());
        userRepository.save(t);
    }

    public void modify(Long id, UserRequest test) {
        Optional<User> testOptional = userRepository.findById(id);
        if (testOptional.isPresent()) {
            User t = testOptional.get();
            t.setName(test.getName());
            userRepository.save(t);
        }
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
