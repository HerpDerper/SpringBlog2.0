package com.example.SpringRealBlog.Controllers;

import com.example.SpringRealBlog.Models.User;
import com.example.SpringRealBlog.Repositories.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@PreAuthorize("hasAnyAuthority('ADMIN')")
@Controller
public class UserController {
    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final ContactDataRepository contactDataRepository;

    private final CommunitySubscriberRepository communitySubscriberRepository;

    private final CommunityRepository communityRepository;

    private final CommunityOwnerRepository communityOwnerRepository;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, CommentRepository commentRepository, PostRepository postRepository,
                          ContactDataRepository contactDataRepository, CommunitySubscriberRepository communitySubscriberRepository,
                          CommunityRepository communityRepository, CommunityOwnerRepository communityOwnerRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.contactDataRepository = contactDataRepository;
        this.communitySubscriberRepository = communitySubscriberRepository;
        this.communityRepository = communityRepository;
        this.communityOwnerRepository = communityOwnerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/user/index")
    public String userIndex(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("adminAccess", true);
        return "User/Index";
    }

    @GetMapping("/user/index")
    public String userFilter(@RequestParam(required = false) String surname, Model model) {
        Iterable<User> users;
        model.addAttribute("adminAccess", true);
        if (surname != null && !surname.equals("")) {
             users = userRepository.findBySurnameContains(surname);
        } else users = userRepository.findAll();
        model.addAttribute("users", users);
        return "User/Index";
    }

    @PostMapping("/user/details")
    public String userDetails(@RequestParam long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        model.addAttribute("adminAccess", true);
        return "User/Details";
    }

    @PostMapping("/user/editUser")
    public String userEdit(@ModelAttribute("user") @Valid User userValid, BindingResult bindingResult, Model model) {
        model.addAttribute("adminAccess", true);
        User user = userRepository.findById(userValid.getId()).get();
        if (bindingResult.hasErrors()) return "/User/Edit";
        user.setName(userValid.getName());
        user.setSurname(userValid.getSurname());
        user.setPatronymic(userValid.getPatronymic());
        user.setUsername(userValid.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDateBirth(userValid.getDateBirth());
        userRepository.save(user);
        return "redirect:/user/index";
    }

    @PostMapping("/user/edit")
    public String userEdit(@RequestParam long id, Model model) {
        User user = userRepository.findById(id).get();
        model.addAttribute("adminAccess", true);
        model.addAttribute("user", user);
        return "User/Edit";
    }
}