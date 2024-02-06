package com.nanda.springboot.controller;

import com.nanda.springboot.entity.User;
import com.nanda.springboot.model.CreateUserRequest;
import com.nanda.springboot.model.SearchUserRequest;
import com.nanda.springboot.model.UserResponse;
import com.nanda.springboot.model.WebResponse;
import com.nanda.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(
            path = "/api/user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> create(@RequestBody CreateUserRequest request){
        System.out.println("Request name: " + request.getName());

        UserResponse userResponse = userService.create(request);

        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }

    @GetMapping(
            path = "/api/users",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<UserResponse>>search(User user,
                                           @RequestParam(value = "name",required = false) String name){

        SearchUserRequest request = SearchUserRequest.builder()
                .name(name).build();

        Page<UserResponse> userResponses = userService.getList(user,request);

        return WebResponse.<List<UserResponse>>builder()
                .data(userResponses.getContent()).build();
    }
}
