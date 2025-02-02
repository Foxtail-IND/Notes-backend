package com.foxtail.NotesBackend.controllers;

import com.foxtail.NotesBackend.models.Users;
import com.foxtail.NotesBackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user-info")
    public Users user(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        Optional<Users> user = userRepository.findByEmail(email);

        return user.orElse(null);
    }

    @GetMapping("/logout")
    public String logout() {
        return "Logout successful!";
    }

}
