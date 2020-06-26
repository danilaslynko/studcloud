package com.studentcloud.dbaccess.repo;

import com.studentcloud.dbaccess.entities.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface DepartmentRepo extends CrudRepository<Department, Integer> {

    boolean existsByName(String name);

    Department findByName(String name);

    Set<Department> findAllByUniversity_Name(String universityName);
}
