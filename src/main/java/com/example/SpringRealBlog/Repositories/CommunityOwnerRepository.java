package com.example.SpringRealBlog.Repositories;

import com.example.SpringRealBlog.Models.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommunityOwnerRepository extends CrudRepository<CommunityOwner, Long> {

    List<CommunityOwner> findByUser (User user);

    List<CommunityOwner> findByCommunity (Community community);
}
