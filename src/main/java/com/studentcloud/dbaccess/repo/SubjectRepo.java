package com.studentcloud.dbaccess.repo;

import com.studentcloud.dbaccess.entities.Subject;
import com.studentcloud.dbaccess.entities.University;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface SubjectRepo extends CrudRepository<Subject, Integer> {

    boolean existsByName(String name);

    Subject findByName(String name);

    Set<Subject> findAllByUniversitiesContains(University university);
}
