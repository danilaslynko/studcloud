package com.studentcloud.dbaccess.repo;

import com.studentcloud.dbaccess.auth.User;
import com.studentcloud.dbaccess.entities.File;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface FileRepo extends CrudRepository<File, Long> {

    Set<File> findAllByDepartment_Name(String department_name);

    Set<File> findAllByUniversity_Name(String university_name);

    Set<File> findAllByTeacher_Name(String teacher_name);

    Set<File> findAllByDepartment_Id(Integer departmentID);

    Set<File> findAllByTeacher_Id(Integer teacherID);

    Set<File> findAllBySubj_Id(Integer subjID);

    Set<File> findAllByUser(User user);
}
