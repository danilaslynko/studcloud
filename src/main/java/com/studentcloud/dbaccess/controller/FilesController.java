package com.studentcloud.dbaccess.controller;

import com.studentcloud.dbaccess.entities.File;
import com.studentcloud.dbaccess.entities.University;
import com.studentcloud.dbaccess.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.util.Optional;

@Controller
@RequestMapping("/files")
public class FilesController {
    final FileService fileService;

    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public String universities(Model model){
        Iterable<University> universities = fileService.getUniversities();
        model.addAttribute("universities", universities);
        return "universities";
    }

    @GetMapping("/universities/{universityName}")
    public String filesInUniversity(
            Model model,
            @PathVariable String universityName,
            @RequestParam Long departmentID,
            @RequestParam Long teacherID,
            @RequestParam Long subjectID
    ) {
        fileService.getUniversityFields(model, universityName, departmentID, teacherID, subjectID);

        return "files";
    }

    @GetMapping("/{fileID}")
    public String getFile(
            Model model,
            @PathVariable Long fileID
    ) {
        Optional<File> file = fileService.findById(fileID);
        if (file.isPresent()) model.addAttribute("file", file);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Запрашиваемый файл не существует");

        return "file";
    }

    @PostMapping("/{fileID}")
    public String addComment(
            Model model,
            @RequestParam String message,
            @PathVariable Long fileID
    ) {
        Optional<File> file = fileService.addComment(message, fileID);
        model.addAttribute("file", file);

        return "file";
    }

    @GetMapping("/{fileID}/download")
    public ResponseEntity<Object> downloadFile(
            @PathVariable Long fileID
    ) {
        try {
            return fileService.downloadFile(fileID);
        } catch (FileNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "file doesn't exist on server");
        }
    }
}
