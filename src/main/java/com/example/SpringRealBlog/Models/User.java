package com.example.SpringRealBlog.Models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = "[a-zA-Zа-яА-Я]{1,30}", message = "Имя должно быть от 1 до 30 символов и состоять только из букв")
    private String name;

    @Pattern(regexp = "[a-zA-Zа-яА-Я]{1,30}", message = "Фамилия должна быть от 1 до 30 символов и состоять только из букв")
    private String surname;

    @Pattern(regexp = "[a-zA-Zа-яА-Я]{0,30}", message = "Отчество должно быть от 1 до 30 символов и состоять только из букв")
    private String patronymic;

    @NotNull(message = "Дата рождения не должна быть пустой")
    @Past(message = "Вы путешествуете во времени?")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dateBirth;

    @OneToOne
    @JoinColumn(name = "contactDataId", referencedColumnName = "id")
    private ContactData contactData;

    @NotNull
    @OneToOne
    @JoinColumn(name = "accountId", referencedColumnName = "id")
    private Account account;

    @ManyToMany
    @JoinTable(name = "postLike", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "postId"))
    public List<Post> likedPosts;

    @ManyToMany
    @JoinTable(name = "commentLike", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "commentId"))
    public List<Comment> likedComments;

    @ManyToMany
    @JoinTable(name = "subscriber", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "communityId"))
    public List<Community> subscribedCommunities;

    public User() {
    }

    public User(String name, String surname, String patronymic, Date dateBirth, ContactData contactData, Account account,
                List<Post> likedPosts, List<Comment> likedComments, List<Community> subscribedCommunities) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.dateBirth = dateBirth;
        this.contactData = contactData;
        this.account = account;
        this.likedPosts = likedPosts;
        this.likedComments = likedComments;
        this.subscribedCommunities = subscribedCommunities;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public ContactData getContactData() {
        return contactData;
    }

    public void setContactData(ContactData contactData) {
        this.contactData = contactData;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(List<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }

    public List<Comment> getLikedComments() {
        return likedComments;
    }

    public void setLikedComments(List<Comment> likedComments) {
        this.likedComments = likedComments;
    }

    public List<Community> getSubscribedCommunities() {
        return subscribedCommunities;
    }

    public void setSubscribedCommunities(List<Community> subscribedCommunities) {
        this.subscribedCommunities = subscribedCommunities;
    }
}