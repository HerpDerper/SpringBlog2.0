package com.example.SpringRealBlog.Repositories;

import com.example.SpringRealBlog.Models.Community;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommunityRepository extends CrudRepository<Community, Long> {

    List<Community> findByNameContains(String name);
}