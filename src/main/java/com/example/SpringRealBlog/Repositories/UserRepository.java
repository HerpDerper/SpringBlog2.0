package com.example.SpringRealBlog.Repositories;

import com.example.SpringRealBlog.Models.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    Iterable<User> findBySurnameContains(String surname);

    User findUserByUsername(String username);
}