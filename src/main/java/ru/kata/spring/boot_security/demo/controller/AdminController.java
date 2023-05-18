package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.LoadUserSecurity;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final LoadUserSecurity loadUserSecurity;

    public AdminController(UserService userService, RoleService roleService,
                           LoadUserSecurity loadUserSecurity) {
        this.userService = userService;
        this.roleService = roleService;
        this.loadUserSecurity = loadUserSecurity;
    }
    @GetMapping("/admin")
    public String findAll(Model model, Principal principal) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("admin", loadUserSecurity.findByUsername(principal.getName()));
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "table";
    }

    @PostMapping("/saveUser") //сохранение
    public String saveUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }
    @PatchMapping(value = "/admin/{id}")//редактирование
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        User userUpdate = userService.getUser(id);
        userUpdate.setUsername(user.getUsername());
        userUpdate.setPassword(user.getPassword());
        userUpdate.setFirstName(user.getFirstName());
        userUpdate.setLastName(user.getLastName());
        userUpdate.setAge(user.getAge());
        userUpdate.setRoles(user.getRoles());
        userService.updateUser(userUpdate);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}/delete") //удаление
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
