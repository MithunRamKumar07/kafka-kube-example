package com.mithun.kafka.demo.controller;

import com.mithun.kafka.demo.model.UserDTO;
import com.mithun.kafka.demo.mongo.UserDocument;
import com.mithun.kafka.demo.repository.UserRepository;
import com.mithun.kafka.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping
    public List<UserDocument> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/create")
    public UserDTO createUser(@RequestBody UserDTO user) {
        return userService.processUserEvent(user);
    }
}
