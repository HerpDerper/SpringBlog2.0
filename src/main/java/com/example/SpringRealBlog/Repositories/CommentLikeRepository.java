package com.example.SpringRealBlog.Repositories;

import com.example.SpringRealBlog.Models.Comment;
import com.example.SpringRealBlog.Models.CommentLike;
import com.example.SpringRealBlog.Models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentLikeRepository extends CrudRepository<CommentLike, Long> {

    List<CommentLike> findByComment(Comment comment);

    CommentLike findByCommentAndUser(Comment comment, User user);
}