package com.studentcloud.dbaccess.repo;

import com.studentcloud.dbaccess.entities.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepo extends CrudRepository<Comment, Long> {
}
