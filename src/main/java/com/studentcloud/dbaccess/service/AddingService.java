package com.studentcloud.dbaccess.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.studentcloud.dbaccess.auth.User;
import com.studentcloud.dbaccess.entities.*;
import com.studentcloud.dbaccess.repo.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;

@Service
public class AddingService {
    final FileRepo fileRepo;
    final UniversityRepo universityRepo;
    final TeacherRepo teacherRepo;
    final DepartmentRepo departmentRepo;
    final SubjectRepo subjectRepo;
    final CommentRepo commentRepo;
    final UserRepo userRepo;
    final UserService userService;
    final Logger logger;

    @Value("${upload.path}")
    private String uploadPath;
    @Value("${amazon.bucketname}")
    private String bucketName;

    private final AmazonS3 s3client;

    public AddingService(FileRepo fileRepo,
                         UniversityRepo universityRepo,
                         TeacherRepo teacherRepo,
                         DepartmentRepo departmentRepo,
                         SubjectRepo subjectRepo,
                         CommentRepo commentRepo,
                         UserRepo userRepo,
                         UserService userService,
                         Logger logger,
                         AmazonS3 s3client
    ) {
        this.fileRepo = fileRepo;
        this.universityRepo = universityRepo;
        this.teacherRepo = teacherRepo;
        this.departmentRepo = departmentRepo;
        this.subjectRepo = subjectRepo;
        this.commentRepo = commentRepo;
        this.userRepo = userRepo;
        this.userService = userService;
        this.logger = logger;
        this.s3client = s3client;
    }

    public void addFile(
            MultipartFile fileUploaded,
            String universityName,
            String departmentName,
            String teacherName,
            String subjectName,
            String fileName,
            User user
    ) {
        University university;
        Teacher teacher;
        Department department;
        Subject subj;

        university = universityRepo.existsByName(universityName) ?
                universityRepo.findByName(universityName) :
                new University(universityName);

        teacher = teacherRepo.existsByName(teacherName) ?
                teacherRepo.findByName(teacherName) :
                new Teacher(teacherName);

        department = departmentRepo.existsByName(departmentName) ?
                departmentRepo.findByName(departmentName) :
                new Department(departmentName, university);

        subj = subjectRepo.existsByName(subjectName) ?
                subjectRepo.findByName(subjectName) :
                new Subject(subjectName);

        fillEntities(university, department, teacher, subj);

        try {
            fileName = saveFile(
                    fileUploaded,
                    universityName,
                    departmentName,
                    teacherName,
                    subjectName,
                    fileName
            );
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        File file = new File(
                fileName,
                university,
                teacher,
                department,
                subj,
                new java.sql.Date(new java.util.Date().getTime()),
                user
        );

        try {
            fileRepo.save(file);
        } catch (IllegalArgumentException e) {
            logger.warn(e.getLocalizedMessage());
        }

    }

    public String saveFile(
            MultipartFile file,
            String universityName,
            String departmentName,
            String teacherName,
            String subjectName,
            String fileName
    ) throws IOException {
        if (file != null) {

            java.io.File uploadDir = new java.io.File(uploadPath);

            if (!s3client.doesBucketExist(bucketName)) {
                try {
                    s3client.createBucket(bucketName);
                } catch (SdkClientException e) {

                }
            }

            if (!uploadDir.exists()) {
                if (!uploadDir.mkdir()) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            StringBuilder resultFileNameBuilder = new StringBuilder(universityName);
            resultFileNameBuilder
                    .append("-")
                    .append(departmentName)
                    .append("-")
                    .append(teacherName)
                    .append("-")
                    .append(subjectName)
                    .append("-")
                    .append(fileName)
                    .append("-")
                    .append(file.getOriginalFilename());

            String resultFileName = resultFileNameBuilder.toString();

            java.io.File fileBuffer = new java.io.File(
                    uploadDir.getAbsolutePath() + java.io.File.separatorChar + resultFileName
            );

            file.transferTo(fileBuffer);

            s3client.putObject(
                    bucketName,
                    resultFileName,
                    fileBuffer
            );

            fileBuffer.delete();

            return resultFileName;
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void fillEntities(
            University university,
            Department department,
            Teacher teacher,
            Subject subj
    ) {
        university.getTeachers().add(teacher);
        university.getDepartments().add(department);
        university.getSubjects().add(subj);

        department.getTeachers().add(teacher);
        department.getSubjects().add(subj);

        subj.getDepartments().add(department);
        subj.getTeachers().add(teacher);
        subj.getUniversities().add(university);

        teacher.getDepartments().add(department);
        teacher.getSubjects().add(subj);
        teacher.getUniversities().add(university);
    }
}
