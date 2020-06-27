package com.studentcloud.dbaccess.repo;

import com.studentcloud.dbaccess.entities.University;
import org.springframework.data.repository.CrudRepository;

public interface UniversityRepo extends CrudRepository<University, Long> {

    boolean existsByName(String name);

    University findByName(String name);
}
