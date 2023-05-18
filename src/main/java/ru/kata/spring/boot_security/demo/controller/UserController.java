package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.LoadUserSecurity;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class UserController {
    private final UserService userService;
    private final LoadUserSecurity loadUserSecurity;

    public UserController(UserService userService, LoadUserSecurity loadUserSecurity) {
        this.userService = userService;
        this.loadUserSecurity = loadUserSecurity;
    }

    @GetMapping("/user")
    public String findAll(Model model, Principal principal) {
        model.addAttribute("user", loadUserSecurity.findByUsername(principal.getName()));
        return "user";
    }
}
