package com.example.SpringRealBlog.Repositories;

import com.example.SpringRealBlog.Models.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findByDescriptionContains(String description);

    List<Post> findByThematic (Thematic thematic);

    List<Post> findByCommunityOwner_Community(Community community);

    List<Post> findByCommunityOwner_User(User user);

    List<Post> findByDescriptionContainsAndCommunityOwner_Community(String description, Community community);
}