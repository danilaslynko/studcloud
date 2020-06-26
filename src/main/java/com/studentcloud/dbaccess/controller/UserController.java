package com.studentcloud.dbaccess.controller;

import com.studentcloud.dbaccess.auth.User;
import com.studentcloud.dbaccess.service.UserService;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public @URL String getID(@AuthenticationPrincipal User user) {
        return "redirect:/user/" + user.getId();
    }

    @GetMapping("/{userID}")
    public String getUser(@PathVariable Long userID, Model model) {
        User user = userService.findById(userID).get();
        model.addAttribute("user", user);

        return "user";
    }

    @GetMapping("/activate")
    public String activate(
            @RequestParam String activationCode,
            Model model
    ) {
        userService.activateUser(activationCode);

        model.addAttribute("message", "Пользователь активирован!");

        return "login";
    }

    @GetMapping("/update")
    public String getCredentials(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);

        return "editprofile";
    }

    @PostMapping("/update")
    public @URL String updateUser(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email
    ) {
        userService.updateProfile(user, password, email);

        return "redirect:/login";
    }
}
