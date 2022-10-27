package com.example.SpringRealBlog.Models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = "[a-zA-Zа-яА-Я]{1,50}", message = "Название тематики должно быть от 1 до 50 символов и состоять только из букв")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dateCreation;

    @ManyToMany
    @JoinTable(name = "subscriber", joinColumns = @JoinColumn(name = "communityId"), inverseJoinColumns = @JoinColumn(name = "userId"))
    public List<User> subscribedUsers;

    public Community() {
    }

    public Community(String name, Date dateCreation, List<User> subscribedUsers) {
        this.name = name;
        this.dateCreation = dateCreation;
        this.subscribedUsers = subscribedUsers;
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

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public List<User> getSubscribedUsers() {
        return subscribedUsers;
    }

    public void setSubscribedUsers(List<User> subscribedUsers) {
        this.subscribedUsers = subscribedUsers;
    }
}
