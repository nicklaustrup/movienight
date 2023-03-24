package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.ExampleCreateRequest;
import com.kenzie.appserver.controller.model.ExampleResponse;
import com.kenzie.appserver.service.ExampleService;
import com.kenzie.appserver.service.UserService;
import com.kenzie.appserver.service.model.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ExampleResponse> get(@PathVariable("id") String id) {
//
//        Example example = userService.findById(id);
//        if (example == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        ExampleResponse exampleResponse = new ExampleResponse();
//        exampleResponse.setId(example.getId());
//        exampleResponse.setName(example.getName());
//        return ResponseEntity.ok(exampleResponse);
//    }
//
//    @PostMapping
//    public ResponseEntity<ExampleResponse> addNewConcert(@RequestBody ExampleCreateRequest exampleCreateRequest) {
//        Example example = new Example(randomUUID().toString(),
//                exampleCreateRequest.getName());
//        exampleService.addNewExample(example);
//
//        ExampleResponse exampleResponse = new ExampleResponse();
//        exampleResponse.setId(example.getId());
//        exampleResponse.setName(example.getName());
//
//        return ResponseEntity.created(URI.create("/example/" + exampleResponse.getId())).body(exampleResponse);
//    }
}