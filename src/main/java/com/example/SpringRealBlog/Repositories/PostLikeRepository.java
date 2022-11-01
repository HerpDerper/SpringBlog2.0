package com.example.SpringRealBlog.Repositories;

import com.example.SpringRealBlog.Models.Post;
import com.example.SpringRealBlog.Models.PostLike;
import com.example.SpringRealBlog.Models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostLikeRepository extends CrudRepository<PostLike, Long> {

    List<PostLike> findByPost(Post post);

    PostLike findByPostAndUser(Post post, User user);
}