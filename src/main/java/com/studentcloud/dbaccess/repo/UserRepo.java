package com.studentcloud.dbaccess.repo;


import com.studentcloud.dbaccess.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Optional<User> findByActivationCode(String activationCode);
}
