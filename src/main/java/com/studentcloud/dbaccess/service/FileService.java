package com.studentcloud.dbaccess.service;

import com.studentcloud.dbaccess.entities.*;
import com.studentcloud.dbaccess.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import java.awt.datatransfer.DataFlavor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Set;

@Service
public class FileService {
    final FileRepo fileRepo;
    final UniversityRepo universityRepo;
    final TeacherRepo teacherRepo;
    final DepartmentRepo departmentRepo;
    final SubjectRepo subjectRepo;
    final CommentRepo commentRepo;

    @Autowired
    private ServletContext servletContext;

    @Value("${upload.path}")
    private String uploadPath;

    public FileService(FileRepo fileRepo, UniversityRepo universityRepo, TeacherRepo teacherRepo, DepartmentRepo departmentRepo, SubjectRepo subjectRepo, CommentRepo commentRepo) {
        this.fileRepo = fileRepo;
        this.universityRepo = universityRepo;
        this.teacherRepo = teacherRepo;
        this.departmentRepo = departmentRepo;
        this.subjectRepo = subjectRepo;
        this.commentRepo = commentRepo;
    }

    public Iterable<University> getUniversities() {
        return universityRepo.findAll();
    }

    public Set<File> getFiles(
            String universityName,
            Long departmentID,
            Long teacherID,
            Long subjectID
    ) {
        Set<File> filesByUniversity = fileRepo.findAllByUniversity_Name(universityName);

        Set<File> filesByDepartment = !(departmentID == 0) ?
                fileRepo.findAllByDepartment_Id(departmentID) :
                filesByUniversity;
        Set<File> filesByTeacher = !(teacherID == 0) ?
                fileRepo.findAllByTeacher_Id(teacherID) :
                filesByUniversity;
        Set<File> filesBySubj = !(subjectID == 0) ?
                fileRepo.findAllBySubj_Id(subjectID) :
                filesByUniversity;

        filesByUniversity.retainAll(filesByDepartment);
        filesByUniversity.retainAll(filesByTeacher);
        filesByUniversity.retainAll(filesBySubj);

        return filesByUniversity;
    }

    public Optional<File> findById(Long fileID) {
        return fileRepo.findById(fileID);
    }

    public University findUniversity(String name) {
        return universityRepo.findByName(name);
    }

    public Optional<File> addComment(String message, Long fileID) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<File> file = this.findById(fileID);

        name = StringUtils.isEmpty(name) ? "Анон" : name;

        Comment comment = new Comment(file.get(), name, message, new java.sql.Date((new java.util.Date()).getTime()));
        file.get().getComments().add(comment);

        commentRepo.save(comment);

        return file;
    }

    public ResponseEntity<Object> downloadFile(Long fileID) throws FileNotFoundException {
        Optional<File> fileOpt = fileRepo.findById(fileID);

        String fileName = fileOpt.get().getName();

        java.io.File file = new java.io.File(
                uploadPath + java.io.File.separatorChar + fileName
        );

        MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
        String mimeType = fileTypeMap.getContentType(fileName);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                // Content-Type
                .contentType(MediaType.valueOf(mimeType))
                // Content-Length
                .contentLength(file.length())
                .body(resource);
    }

    public void getUniversityFields(
            Model model,
            String universityName,
            Long departmentID,
            Long teacherID,
            Long subjectID
    ) {
        University university = this.findUniversity(universityName);

        Set<Department> departments = university.getDepartments();
        Set<Teacher> teachers = university.getTeachers();
        Set<Subject> subjects = university.getSubjects();

        model.addAttribute("departments", departments);
        model.addAttribute("teachers", teachers);
        model.addAttribute("subjects", subjects);

        Set<File> files = getFiles(universityName, departmentID, teacherID, subjectID);
        model.addAttribute("files", files);
    }

    public void deleteFile(Long fileID) {

        File fileEntity = fileRepo.findById(fileID).get();
        java.io.File file = new java.io.File(fileEntity.getName());
        file.delete();

        fileRepo.delete(fileEntity);

    }

    public void deleteComment(Long fileID, Long commentID, Model model) {
        File file = fileRepo.findById(fileID).get();
        Comment comment = commentRepo.findById(commentID).get();

        fileRepo.save(file);
        commentRepo.delete(comment);

        model.addAttribute("file", file);
    }

    public void getEditPage(Long fileID, Model model) {
        File file = fileRepo.findById(fileID).get();
        model.addAttribute("file", file);
    }
}
