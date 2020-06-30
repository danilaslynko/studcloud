package com.studentcloud.dbaccess.controller;

import com.studentcloud.dbaccess.entities.File;
import com.studentcloud.dbaccess.service.FileService;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("file/{fileID}/edit")
public class EditController {

    private final FileService fileService;

    public EditController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public String getEditPage(@RequestParam Long fileID, Model model) {
        File file = fileService.findById(fileID).get();
        model.addAttribute("file", file);

        return "edit";
    }

    @PostMapping("/delete")
    public @URL String deleteFile(@PathVariable Long fileID) {

        fileService.deleteFile(fileID);

        return "redirect:/files";
    }

    @PostMapping("/{commentID}deleteComment")
    public String deleteComment(
            @PathVariable Long fileID,
            @PathVariable Long commentID,
            Model model
    ) {

        fileService.deleteComment(fileID, commentID, model);

        return "edit";
    }
}
