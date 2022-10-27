package com.example.SpringRealBlog.Repositories;

import com.example.SpringRealBlog.Models.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContactDataRepository extends CrudRepository<ContactData, Long> {

    List<ContactData> findByEmail(String email);
}