package com.example.SpringRealBlog.Controllers;

import com.example.SpringRealBlog.Models.Role;
import com.example.SpringRealBlog.Models.User;
import com.example.SpringRealBlog.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class RegistrationController {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;


    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/registration")
    public String userCreate(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if(userRepository.findUserByUsername(user.getUsername()) != null) {
            ObjectError error = new ObjectError("username", "Логин уже занят");
            bindingResult.addError(error);
        }
        if (bindingResult.hasErrors()) return "User/Create";
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String userCreate(@ModelAttribute("user") User user) {
        return "User/Create";
    }
}