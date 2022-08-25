package com.example.springwebservice.controller;

import com.example.springwebservice.controller.dto.request.CreateUserRequest;
import com.example.springwebservice.controller.dto.request.UpdateUserRequest;
import com.example.springwebservice.controller.dto.response.StatusResponse;
import com.example.springwebservice.model.UserRepository;
import com.example.springwebservice.model.entity.User;
import com.example.springwebservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/practice/user")
public class PracticeController {

    @Autowired
    private  UserService userService;

    @GetMapping()
    public List<User> getUserByNameAndAge(@RequestParam String name, @RequestParam int age) {
        List<User> response = this.userService.findByNameAndAge(name, age);
        return response;
    }

    @PostMapping()
    public StatusResponse createUserBySql(@RequestBody CreateUserRequest createUserRequest) {
        String response = this.userService.createUserBySql(createUserRequest);
        return new StatusResponse(response);
    }

    @PutMapping("/{id}")
    public StatusResponse updateUserBySql(@PathVariable int id, @RequestBody UpdateUserRequest updateUserRequest) {
        String response = this.userService.updateUserBySql(id, updateUserRequest);
        return new StatusResponse(response);
    }

    @DeleteMapping()
    public StatusResponse deleteUserBySql(@RequestParam String name, @RequestParam int age) {
        String response = this.userService.deleteUserBySql(name, age);
        return new StatusResponse(response);
    }
}
