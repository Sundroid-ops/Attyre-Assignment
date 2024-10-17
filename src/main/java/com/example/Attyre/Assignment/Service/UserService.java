package com.example.Attyre.Assignment.Service;

import com.example.Attyre.Assignment.DTO.UserDTO;
import com.example.Attyre.Assignment.Entity.Product;
import com.example.Attyre.Assignment.Entity.User;

import java.util.List;

public interface UserService {
    public User saveUser(UserDTO userDTO);

    public User getUserByID(Long ID);

}
