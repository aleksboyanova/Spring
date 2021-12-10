package com.uni.project.repository;

import com.uni.project.entities.Comment;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
    List<Comment> findByListingId(Integer listingId);
}
