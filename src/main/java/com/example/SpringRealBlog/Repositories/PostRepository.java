package com.example.SpringRealBlog.Repositories;

import com.example.SpringRealBlog.Models.Community;
import com.example.SpringRealBlog.Models.Post;
import com.example.SpringRealBlog.Models.Thematic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findByThematic (Thematic thematic);

    List<Post> findByCommunityOwner_Community(Community community);

    List<Post> findByDescriptionContains(String description);
}