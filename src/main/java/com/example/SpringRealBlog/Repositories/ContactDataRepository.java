package com.example.SpringRealBlog.Repositories;

import com.example.SpringRealBlog.Models.ContactData;
import org.springframework.data.repository.CrudRepository;

public interface ContactDataRepository extends CrudRepository<ContactData, Long> { }