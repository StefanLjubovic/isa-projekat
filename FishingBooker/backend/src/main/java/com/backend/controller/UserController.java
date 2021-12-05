package com.backend.controller;

import com.backend.model.RegisteredUser;
import com.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/allUsers")
    public ResponseEntity<List<RegisteredUser>> getAllUsers() {
        List<RegisteredUser> users = this.userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
