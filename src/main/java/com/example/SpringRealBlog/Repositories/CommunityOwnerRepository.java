package com.example.SpringRealBlog.Repositories;

import com.example.SpringRealBlog.Models.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommunityOwnerRepository extends CrudRepository<CommunityOwner, Long> {

    List<CommunityOwner> findByCommunity(Community community);

    CommunityOwner findByCommunityAndUser(Community community, User user);
}
