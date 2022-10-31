package com.example.SpringRealBlog.Repositories;

import com.example.SpringRealBlog.Models.Community;
import com.example.SpringRealBlog.Models.CommunitySubscriber;
import com.example.SpringRealBlog.Models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommunitySubscriberRepository extends CrudRepository<CommunitySubscriber, Long> {

    List<CommunitySubscriber> findByUser (User user);

    List<CommunitySubscriber> findByCommunity (Community community);

    CommunitySubscriber findByCommunityAndUser(Community community, User user);
}