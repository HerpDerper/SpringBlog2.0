package com.example.SpringRealBlog.Repositories;

import com.example.SpringRealBlog.Models.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommunityRepository extends CrudRepository<Community, Long> {

    List<Community> findByName(String name);

    List<Community> findByNameContains(String name);
}