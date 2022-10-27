package com.example.SpringRealBlog.Models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
public class ContactData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Адрес не должен быть пустым")
    private String address;

    @NotBlank(message = "Email не должен быть пустым")
    @Email(regexp = "[a-zA-Z0-9]{3,20}@[a-zA-Z0-9]{3,15}[.][a-zA-Z]{2,5}", message= "Некорретный ввод электронной почты")
    private String email;

    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7}$", message = "Некорретный ввод номера телефона")
    private String phone;

    public ContactData() {
    }

    public ContactData(String address, String email, String phone) {
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
