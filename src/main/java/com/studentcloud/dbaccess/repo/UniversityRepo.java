package com.studentcloud.dbaccess.repo;

import com.studentcloud.dbaccess.entities.University;
import org.springframework.data.repository.CrudRepository;

public interface UniversityRepo extends CrudRepository<University, Integer> {

    boolean existsByName(String name);

    University findByName(String name);
}
