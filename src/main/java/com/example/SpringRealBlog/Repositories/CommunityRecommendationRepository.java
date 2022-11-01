package com.example.SpringRealBlog.Repositories;

import com.example.SpringRealBlog.Models.Community;
import com.example.SpringRealBlog.Models.CommunityRecommendation;
import com.example.SpringRealBlog.Models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommunityRecommendationRepository extends CrudRepository<CommunityRecommendation, Long> {

    List<CommunityRecommendation> findByCommunity (Community community);

    CommunityRecommendation findByCommunityAndUser(Community community, User user);
}