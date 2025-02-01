package com.foxtail.NotesBackend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @GetMapping
    public Principal user(Principal principal) {
        return principal;  // Returns authenticated user details
    }
}
