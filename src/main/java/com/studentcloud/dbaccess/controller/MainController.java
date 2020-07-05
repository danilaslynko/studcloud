package com.studentcloud.dbaccess.controller;

import com.studentcloud.dbaccess.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private FileService fileService;

    @GetMapping(path = "/")
    public String home(Model model){
        model.addAttribute("files", fileService.getAll());

        return "home";
    }

    @GetMapping("/contacts")
    public String contacts() {
        return "contacts";
    }
}
