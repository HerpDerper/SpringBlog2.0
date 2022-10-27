package com.example.SpringRealBlog.Models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Логин не должен быть пустым или состоять из одних лишь пробелов")
    @Column(unique = true)
    @Size(min = 1, max = 16, message = "Логин должен быть от 1 до 16 символов")
    private String login;

    @NotBlank(message = "Пароль не должен быть пустым или состоять из одних лишь пробелов")
    @Size(min = 8, max = 16, message = "Пароль должен быть от 8 до 16 символов")
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "accountRole", joinColumns = @JoinColumn(name = "accountId"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Account() {
    }

    public Account(String login, String password, Set<Role> roles) {
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
