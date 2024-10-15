package com.example.Attyre.Assignment.Controller;

import com.example.Attyre.Assignment.DTO.UserDTO;
import com.example.Attyre.Assignment.Entity.User;
import com.example.Attyre.Assignment.Service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/")
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserDTO userDTO){
        logger.info("Incoming POST request to create User");
        User user = userService.saveUser(userDTO);
        logger.info("Response of User with id: {}", user.getId());
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{ID}")
    public ResponseEntity<User> getUserByID(@PathVariable Long ID){
        logger.info("GET request for finding user by ID: {}", ID);
        User user = userService.getUserByID(ID);
        logger.info("Sending user response after finding user by id: {}", ID);
        return ResponseEntity.ok().body(user);
    }
}
