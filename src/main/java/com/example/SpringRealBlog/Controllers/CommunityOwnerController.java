package com.example.SpringRealBlog.Controllers;

import com.example.SpringRealBlog.AdminCheck;
import com.example.SpringRealBlog.Models.CommunityOwner;
import com.example.SpringRealBlog.Repositories.CommunityOwnerRepository;
import com.example.SpringRealBlog.Repositories.CommunityRepository;
import com.example.SpringRealBlog.Repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommunityOwnerController {

    private final CommunityRepository communityRepository;

    private final UserRepository userRepository;

    private final CommunityOwnerRepository communityOwnerRepository;

    public CommunityOwnerController(CommunityRepository communityRepository, UserRepository userRepository,
                                    CommunityOwnerRepository communityOwnerRepository) {
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
        this.communityOwnerRepository = communityOwnerRepository;
    }

    @PostMapping("/communityOwner/create")
    public String communityCreate(@ModelAttribute("communityOwner") CommunityOwner communityOwner,
                                  @RequestParam long communityId, @RequestParam long userId, Model model) {
        communityOwner.setCommunity(communityRepository.findById(communityId).get());
        communityOwner.setUser(userRepository.findById(userId).get());
        communityOwnerRepository.save(communityOwner);
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(SecurityContextHolder.getContext().getAuthentication()));
        return "redirect:/community/index";
    }

    @GetMapping("/communityOwner/create")
    public String communityOwnerCreate(@ModelAttribute("communityOwner") CommunityOwner communityOwner, Model model,
                                       @RequestParam long communityId) {
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(SecurityContextHolder.getContext().getAuthentication()));
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("users", communityRepository.findById(communityId).get());
        return "CommunityOwner/Create";
    }
}