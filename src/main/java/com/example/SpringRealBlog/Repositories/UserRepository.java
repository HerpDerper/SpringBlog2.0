package com.example.SpringRealBlog.Repositories;

import com.example.SpringRealBlog.Models.*;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    Iterable<User> findBySurname(String surname);

    Iterable<User> findBySurnameContains(String surname);

    User findUserByAccount(Account account);
}