package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.*;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.UserService;
import com.kenzie.appserver.service.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> get(@PathVariable("userId") String id) {

        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping
    public ResponseEntity<UserResponse> addNewUser(@RequestBody UserCreateRequest userCreateRequest) {
        User user = new User(randomUUID().toString(),
                userCreateRequest.getFirstName(),
                userCreateRequest.getLastName());

        userService.addNewUser(user);

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());

        return ResponseEntity.created(URI.create("/user/" + userResponse.getUserId())).body(userResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        List<UserRecord> allUsers = userService.findAll();

        if (allUsers.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        List<UserResponse> allUserResponse = new ArrayList<>();

        for (UserRecord user: allUsers) {
            UserResponse userResponse = new UserResponse();
            userResponse.setUserId(user.getUserId());
            userResponse.setFirstName(user.getFirstName());
            userResponse.setLastName(user.getLastName());
            allUserResponse.add(userResponse);
        }

        return ResponseEntity.ok(allUserResponse);
    }
}

