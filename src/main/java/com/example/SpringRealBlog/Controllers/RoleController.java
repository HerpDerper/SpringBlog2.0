package com.example.SpringRealBlog.Controllers;

import com.example.SpringRealBlog.Models.Role;
import com.example.SpringRealBlog.Models.User;
import com.example.SpringRealBlog.Repositories.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@PreAuthorize("hasAnyAuthority('ADMIN')")
@Controller
public class RoleController {

    private final UserRepository userRepository;

    public RoleController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/role/editToAdmin")
    public String roleAdmin(@RequestParam long userId, Model model) {
        model.addAttribute("adminAccess", true);
        User user = userRepository.findById(userId).get();
        user.getRoles().clear();
        user.getRoles().add(Role.ADMIN);
        userRepository.save(user);
        return "redirect:/user/index";
    }

    @PostMapping("/role/editToUser")
    public String roleUser(@RequestParam long userId, Model model) {
        model.addAttribute("adminAccess", true);
        User user = userRepository.findById(userId).get();
        user.getRoles().clear();
        user.getRoles().add(Role.USER);
        userRepository.save(user);
        return "redirect:/user/index";
    }
}
