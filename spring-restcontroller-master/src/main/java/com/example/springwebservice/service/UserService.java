package com.example.springwebservice.service;

import com.example.springwebservice.controller.dto.request.CreateUserRequest;
import com.example.springwebservice.controller.dto.request.UpdateUserRequest;
import com.example.springwebservice.model.UserRepository;
import com.example.springwebservice.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    List<User> userList;

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUserList(int age, String AgeFilter, String sorted) {

        List<User> response = userRepository.findAll();

        if(-1 == age){
            switch (sorted){
                case "desc":
                    response = userRepository.findByOrderByAgeDesc();

                case  "asc":
                    response = userRepository.findByOrderByAgeAsc();
            }
        }

        if("0".equals(AgeFilter)){
            response = userRepository.findAll();
        }

        else if("1".equals(AgeFilter)){
            response = userRepository.findByAgeGreaterThanEqual(age);
        }



        return response;
    }

    public List<User> getAllUser() {
        List<User> response = userRepository.findAll();
        return response;
    }
    public User getUser(int id) {
        User response = userRepository.findById(id);
        return response;
    }

    public String createUser(CreateUserRequest request) {
//        User response = userRepository.save(user);
        User user = new User();
        user.setName(request.getName());
        user.setAge(request.getAge());
        user.setId(request.getId());
        userRepository.save(user);
        return "OK";
    }

    public String updateUser(int id, UpdateUserRequest user) {
       User updatedUser = userRepository.findById(id);
       if(null == updatedUser){
           return "Fail";
       }
       updatedUser.setAge(user.getAge());
       updatedUser.setName(user.getName());
        userRepository.save(updatedUser);
        return "OK";
    }

    public String deleteUser(int id) {
        User deletedUser = userRepository.findById(id);
        if(null == deletedUser){
            return "Fail";
        }
        userRepository.deleteById(id);
        return "Delete Complete";
    }

    public List<User> findByNameAndAge(String name, int age){
        List<User> response = userRepository.findByNameAndAge(name, age);
        return response;
    }

    public String createUserBySql(CreateUserRequest request) {
        userRepository.createUserBySql(request.getId(), request.getName(), request.getAge());//順序不能亂
        return "OK";
    }

    public String updateUserBySql(int id, UpdateUserRequest request) {
        int count = userRepository.updateUserBySql(request.getAge(),request.getName(), id);
        String response = "FAIL";
        if(count > 0){
            response = "SUCCESS";
        }

        return response;
    }

    public String deleteUserBySql(String name, int age){
        userRepository.deleteUserBySql(name, age);
        return "OK";
    }

//    public List<User> getAllUserWithoutRepeatedName() {
//
//        List<User> userList = userRepository.findAll();
//
//        List<User> sortedUserList = userList.stream().sorted().collect(
//                Collectors.collectingAndThen(
//                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(User:: getName))),ArrayList::new
//                )
//        );
//        return sortedUserList;
//    }

//    public List<User> getAllUserMap() {
//        Map<Integer, String> MapUserList = userList.stream().collect(Collectors.toMap(User::getId, User::getName));
//        return MapUserList;
//
//    }
} // Class end
