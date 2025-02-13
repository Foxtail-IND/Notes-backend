package com.test.sec.service;

import com.test.sec.dto.UserDTO;
import com.test.sec.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void updateUserRole(Long userId, String roleName);

    List<User> getAllUsers();

    UserDTO getUserById(Long id);

    User findByUsername(String username);
}
