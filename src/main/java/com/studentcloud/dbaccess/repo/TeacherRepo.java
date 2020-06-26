package com.studentcloud.dbaccess.repo;

import com.studentcloud.dbaccess.entities.Teacher;
import com.studentcloud.dbaccess.entities.University;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface TeacherRepo extends CrudRepository<Teacher, Integer> {

    boolean existsByName(String name);

    Teacher findByName(String name);

    Set<Teacher> findAllByUniversitiesContains(University university);
}
