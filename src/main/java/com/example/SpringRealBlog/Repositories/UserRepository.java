package com.example.SpringRealBlog.Repositories;

import com.example.SpringRealBlog.Models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    Iterable<User> findBySurnameContains(String surname);

    User findUserByUsername(String username);
}