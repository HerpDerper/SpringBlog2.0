package com.example.SpringRealBlog.Models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Логин не должен быть пустым или состоять из одних лишь пробелов")
    @Size(min = 1, max = 16, message = "Логин должен быть от 1 до 16 символов")
    @Column(unique = true)
    private String username;

    // @Pattern(regexp = "[a-zA-Zа-яА-Я0-9]{1,30}", message = "Имя должно быть от 1 до 30 символов и состоять только из букв")
    @NotBlank(message = "Пароль не должен быть пустым или состоять из одних лишь пробелов")
    private String password;

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

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "userRole", joinColumns = @JoinColumn(name = "userId"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToOne
    @JoinColumn(name = "contactDataId", referencedColumnName = "id")
    private ContactData contactData;

    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public User() {
    }

    public User(String username, String password, String name, String surname, String patronymic, Date dateBirth,
                Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.dateBirth = dateBirth;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public ContactData getContactData() {
        return contactData;
    }

    public void setContactData(ContactData contactData) {
        this.contactData = contactData;
    }
}