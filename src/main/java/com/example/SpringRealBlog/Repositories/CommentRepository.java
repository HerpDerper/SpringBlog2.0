package com.example.SpringRealBlog.Repositories;

import com.example.SpringRealBlog.Models.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findCommentByPostAndTextContains(Post post, String text);

    List<Comment> findCommentByPostAndText(Post post, String text);

    List<Comment> findByPost(Post post);

    List<Comment> findByUser(User user);
}