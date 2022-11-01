package com.example.SpringRealBlog.Repositories;

import com.example.SpringRealBlog.Models.Thematic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ThematicRepository extends CrudRepository<Thematic, Long> {

    Thematic findByName(String name);
}