package com.example.springwebservice.controller;

import com.example.springwebservice.controller.dto.request.CreateUserRequest;
import com.example.springwebservice.controller.dto.request.UpdateUserRequest;
import com.example.springwebservice.controller.dto.response.StatusResponse;
import com.example.springwebservice.model.entity.User;
import com.example.springwebservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:8081/")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping()
    public List<User> getAllUsers(@RequestParam(required = false) int age, @RequestParam(required = false) String ageFilter, @RequestParam String sorted) {
        List<User> response = this.userService.getAllUserList(age, ageFilter, sorted);

        return response;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> response = this.userService.getAllUser();

        return response;
    }

    @GetMapping("/distinct")
    public List<String> getAllUserWithoutRepeatedName() {
        List<User> response = this.userService.getAllUser();
        List<String> distinct = response.stream().map(User :: getName).distinct().sorted().collect(Collectors.toList());
        return distinct;
    }

    @GetMapping("/map")
    public Map<Integer, String> getAllUserWithMap() {
        List<User> response = this.userService.getAllUser();
        Map<Integer, String> map = response.stream().collect(Collectors.toMap(User :: getId, User :: getName));
        return map;
    }

    @GetMapping("/first/{name}")
    public User getFirstUserByName(@PathVariable String name) {
        List<User> response = this.userService.getAllUser();
        List<User> result = response.stream().filter(x -> x.getName().equals(name)).collect(Collectors.toList());
        Optional<User> first = result.stream().findFirst();
        if(first.isPresent()){
            User user = first.get();
            return user;
        }
        else{
            return null;
        }
    }

    @GetMapping("/sorted")
    public List<User> sortedByAgeAndId() {
        List<User> response = this.userService.getAllUser();
        List <User> sorted = response.stream().sorted(Comparator.comparing(User :: getAge).thenComparing(User :: getId))
                                              .collect(Collectors.toList());

        return sorted;
    }

    @GetMapping("/string")
    public String getAllUserInString() {
        List<User> response = this.userService.getAllUser();
        String result = response.stream().map(p -> p.getName()+", "+p.getAge()).collect(Collectors.joining("|"));
        return result;
    }



    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        User user = this.userService.getUser(id);
        return user;
    }

    @PostMapping("/users")
    public StatusResponse createUser(@RequestBody CreateUserRequest createUserRequest) {
        String response = this.userService.createUser(createUserRequest);
        return new StatusResponse(response);
    }

    @PutMapping("/users/{id}")
    public StatusResponse updateUser(@PathVariable int id, @RequestBody UpdateUserRequest updateUserRequest) {
        String response = this.userService.updateUser(id, updateUserRequest);
        return new StatusResponse(response);
    }

    @DeleteMapping("/users/{id}")
    public StatusResponse deleteUser(@PathVariable int id) {
        String response = this.userService.deleteUser(id);
        return new StatusResponse(response);
    }
} // Class end
