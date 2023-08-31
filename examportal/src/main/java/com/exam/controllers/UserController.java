package com.exam.controllers;

import com.exam.models.Role;
import com.exam.models.User;
import com.exam.models.UserRole;
import com.exam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;
    //creating user
    @PostMapping("/")
    public User createUser(@RequestBody User user) throws Exception {
        Set<UserRole> roleSet=new HashSet<>();

        Role role=new Role();
        role.setRoleId(45L);
        role.setRoleName("Normal");
        UserRole userRole=new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        roleSet.add(userRole);


        return this.userService.createUser(user,roleSet);

    }
    //getting user by username

    @GetMapping("/{username}")
    public  User getUser( @PathVariable("username" )String username) throws Exception {


         return this.userService.getUser(username);


    }

    //delete the user by id
    @DeleteMapping("/{userId}")
    public  void deleteUser(@PathVariable("userId") Long userId) throws Exception {

        this.userService.deleteUserById(userId);



    }


}
