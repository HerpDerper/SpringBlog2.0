package com.example.SpringRealBlog.Repositories;

import com.example.SpringRealBlog.Models.Thematic;
import org.springframework.data.repository.CrudRepository;

public interface ThematicRepository extends CrudRepository<Thematic, Long> {

    Thematic findByName(String name);
}