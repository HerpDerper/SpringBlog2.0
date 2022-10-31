package com.example.SpringRealBlog.Models;

import javax.persistence.*;

@Entity
public class CommunitySubscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "communityId", referencedColumnName = "id")
    private Community community;

    public CommunitySubscriber() {
    }

    public CommunitySubscriber(User user, Community community) {
        this.user = user;
        this.community = community;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }
}