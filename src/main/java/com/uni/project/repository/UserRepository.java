package com.uni.project.repository;

import com.uni.project.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface  UserRepository extends CrudRepository<User, Integer> {
    List<User> findByUsernameIs(String name);
    List<User> findByUsernameAndPassword(String username, String password);
}
