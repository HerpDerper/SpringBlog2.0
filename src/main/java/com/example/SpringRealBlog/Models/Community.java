package com.example.SpringRealBlog.Models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = "[a-zA-Zа-яА-Я0-9]{1,30}", message = "Название должно быть от 1 до 30 символов и состоять только из букв и цифр")
    private String name;

    @NotBlank(message = "Описание не должно быть пустым или состоять из одних лишь пробелов")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dateCreation;

    private int subscribersCount;

    private int recommendationsCount;

    public Community() {
    }

    public Community(String name, String description, Date dateCreation, int subscribersCount, int recommendationsCount) {
        this.name = name;
        this.description = description;
        this.dateCreation = dateCreation;
        this.subscribersCount = subscribersCount;
        this.recommendationsCount = recommendationsCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getSubscribersCount() {
        return subscribersCount;
    }

    public void setSubscribersCount(int subscribersCount) {
        this.subscribersCount = subscribersCount;
    }

    public int getRecommendationsCount() {
        return recommendationsCount;
    }

    public void setRecommendationsCount(int recommendationsCount) {
        this.recommendationsCount = recommendationsCount;
    }
}