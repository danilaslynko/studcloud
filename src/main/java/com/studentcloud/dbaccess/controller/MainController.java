package com.studentcloud.dbaccess.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(path = "/")
    public String home(Model model){
        model.addAttribute("title", "Главная страница");
        return "home";
    }

    @GetMapping("/contacts")
    public String contacts() {
        return "contacts";
    }
}
