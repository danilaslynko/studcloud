package com.studentcloud.dbaccess.controller;

import com.studentcloud.dbaccess.auth.User;
import com.studentcloud.dbaccess.service.AddingService;
import com.studentcloud.dbaccess.service.UserService;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AddingController {
    private final AddingService addingService;
    private final UserService userService;

    @Value("${upload.path}")
    private String uploadPath;

    public AddingController(AddingService addingService, UserService userService) {
        this.addingService = addingService;
        this.userService = userService;
    }

    @GetMapping("/add")
    public String add(Model model){
        return "file-add";
    }

    @PostMapping("/add")
    public @URL String addFile(
            @RequestParam("file") MultipartFile fileUploaded,
            @RequestParam String universityName,
            @RequestParam String departmentName,
            @RequestParam String teacherName,
            @RequestParam String subjectName,
            @RequestParam String fileName,
            @AuthenticationPrincipal User userAuth
    ) {
        User user = userService.findById(userAuth.getId()).get();
        addingService.addFile(fileUploaded, universityName, departmentName, teacherName, subjectName, fileName, user);

        return "redirect:/files";
    }
}

