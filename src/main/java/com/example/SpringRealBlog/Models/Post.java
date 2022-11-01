package com.example.SpringRealBlog.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Описание не должно быть пустым или состоять из одних лишь пробелов")
    @Size(min = 1, max = 30, message = "Описание должно быть от 1 до 30 символов")
    private String description;

    @NotBlank(message = "Текст не должен быть пустым или состоять из одних лишь пробелов")
    @Size(min = 1, max = 300, message = "Текст должен быть от 1 до 300 символов")
    private String text;

    private int likeCount;

    @Temporal(TemporalType.DATE)
    private Date dateCreation;

    @ManyToOne
    @JoinColumn(name = "thematicId", referencedColumnName = "id")
    private Thematic thematic;

    @ManyToOne
    @JoinColumn(name = "communityOwnerId", referencedColumnName = "id")
    private CommunityOwner communityOwner;

    public Post() {
    }

    public Post(String description, String text, int likeCount, Date dateCreation, Thematic thematic, CommunityOwner communityOwner) {
        this.description = description;
        this.text = text;
        this.likeCount = likeCount;
        this.dateCreation = dateCreation;
        this.thematic = thematic;
        this.communityOwner = communityOwner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Thematic getThematic() {
        return thematic;
    }

    public void setThematic(Thematic thematic) {
        this.thematic = thematic;
    }

    public CommunityOwner getCommunityOwner() {
        return communityOwner;
    }

    public void setCommunityOwner(CommunityOwner communityOwner) {
        this.communityOwner = communityOwner;
    }
}