package com.example.Attyre.Assignment.Controller;

import com.example.Attyre.Assignment.DTO.UserDTO;
import com.example.Attyre.Assignment.Entity.Enums.Action;
import com.example.Attyre.Assignment.Entity.Product;
import com.example.Attyre.Assignment.Entity.User;
import com.example.Attyre.Assignment.Service.UserInteractionService;
import com.example.Attyre.Assignment.Service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserInteractionService userInteractionService;

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

    @PostMapping("/{userID}/interactions/{productID}/like")
    public void saveLikedInteraction(@PathVariable Long userID, @PathVariable Long productID){
        logger.info("POST request to Like for ProductID: {} by UserID: {}", productID, userID);
        userInteractionService.saveInteraction(userID, productID, Action.LIKED);
    }

    @PostMapping("/{userID}/interactions/{productID}/view")
    public void saveViewedInteraction(@PathVariable Long userID, @PathVariable Long productID){
        logger.info("POST request to View for ProductID: {} by UserID: {}", productID, userID);
        userInteractionService.saveInteraction(userID, productID, Action.VIEWED);
    }

    @GetMapping("/{userID}/interactions")
    public ResponseEntity<List<Product>> getUserInteractedProducts(
            @PathVariable Long userID,
            @RequestParam int page,
            @RequestParam int size){
        logger.info("GET request to View interacted Products for UserID: {}", userID);
        List<Product> products = userInteractionService.getInteraction(userID, page, size);
        return ResponseEntity.ok().body(products);
    }
}
