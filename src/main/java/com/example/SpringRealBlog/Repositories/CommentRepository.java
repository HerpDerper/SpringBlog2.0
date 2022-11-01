package com.example.SpringRealBlog.Repositories;

import com.example.SpringRealBlog.Models.Comment;
import com.example.SpringRealBlog.Models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findByPost(Post post);
}