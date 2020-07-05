package com.studentcloud.dbaccess.repo;

import com.studentcloud.dbaccess.auth.User;
import com.studentcloud.dbaccess.entities.File;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface FileRepo extends CrudRepository<File, Long> {

    Set<File> findAllByDepartment_Name(String department_name);

    Set<File> findAllByUniversity_Name(String university_name);

    Set<File> findAllByTeacher_Name(String teacher_name);

    Set<File> findAllByDepartment_Id(Long departmentID);

    Set<File> findAllByTeacher_Id(Long teacherID);

    Set<File> findAllBySubj_Id(Long subjID);

    Set<File> findAllByUniversity_NameAndDepartment_IdAndTeacher_IdAndSubj_Id(String universityName, Long departmentID, Long teacherID, Long subjID);

    Set<File> findAllByUser(User user);

    Set<File> findTop10ByOrderByIdDesc();
}
