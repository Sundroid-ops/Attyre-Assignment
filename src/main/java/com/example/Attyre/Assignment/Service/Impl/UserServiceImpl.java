package com.example.Attyre.Assignment.Service.Impl;

import com.example.Attyre.Assignment.DTO.UserDTO;
import com.example.Attyre.Assignment.Entity.User;
import com.example.Attyre.Assignment.Exception.InternalServerException;
import com.example.Attyre.Assignment.Repository.UserRepo;
import com.example.Attyre.Assignment.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(UserDTO userDTO) {
        try {
            logger.info("Creating user");
            User user = User.builder()
                    .username(userDTO.getUsername())
                    .gender(userDTO.getGender())
                    .createdAt(LocalDateTime.now())
                    .build();

            User newUser = userRepo.save(user);
            logger.info("User Successfully created for id: {}", newUser.getId());
            return newUser;
        }catch (Exception exception){
            logger.info("Exception occurred during saving user");
            throw new InternalServerException(exception);
        }
    }
}
