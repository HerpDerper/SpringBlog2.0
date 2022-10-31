package com.example.SpringRealBlog.Controllers;

import com.example.SpringRealBlog.AdminCheck;
import com.example.SpringRealBlog.Models.*;
import com.example.SpringRealBlog.Repositories.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CommunityController {

    private final CommunityRepository communityRepository;

    private final UserRepository userRepository;

    private final CommunityOwnerRepository communityOwnerRepository;

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final CommunitySubscriberRepository communitySubscriberRepository;

    public CommunityController(CommunityRepository communityRepository, UserRepository userRepository, CommunityOwnerRepository communityOwnerRepository, PostRepository postRepository, CommentRepository commentRepository, CommunitySubscriberRepository communitySubscriberRepository) {
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
        this.communityOwnerRepository = communityOwnerRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.communitySubscriberRepository = communitySubscriberRepository;
    }

    @PostMapping("/community/create")
    public String communityCreate(@ModelAttribute("community") @Valid Community community, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "redirect:/community/create";
        CommunityOwner communityOwner = new CommunityOwner(userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()), community);
        community.setRecommendationsCount(0);
        community.setSubscribersCount(0);
        community.setDateCreation(new Date());
        communityRepository.save(community);
        communityOwnerRepository.save(communityOwner);
        return "redirect:/community/index";
    }

    @GetMapping("/community/create")
    public String communityCreate(@ModelAttribute("community") Community community, Model model) {
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(SecurityContextHolder.getContext().getAuthentication()));
        return "Community/Create";
    }

    @PostMapping("/community/index")
    public String communityIndex(Model model) {
        Iterable<Community> communities = communityRepository.findAll();
        model.addAttribute("communities", communities);
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(SecurityContextHolder.getContext().getAuthentication()));
        return "Community/Index";
    }

    @GetMapping("/community/index")
    public String communityFilter(@RequestParam(required = false) String name, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(auth));
        Iterable<Community> communities;
        if (name != null && !name.equals("")) {
            communities = communityRepository.findByNameContains(name);
        } else communities = communityRepository.findAll();
        model.addAttribute("communities", communities);
        return "Community/Index";
    }

    @PostMapping("/community/edit")
    public String communityEdit(@RequestParam long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(auth));
        Community community = communityRepository.findById(id).get();
        model.addAttribute("community", community);
        return "Community/Edit";
    }

    @PostMapping("/community/editCommunity")
    public String communityEdit(@ModelAttribute("community") @Valid Community communityValid, BindingResult bindingResult, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(auth));
        Community community = communityRepository.findById(communityValid.getId()).get();
        if (bindingResult.hasErrors()) return "Community/Edit";
        community.setName(communityValid.getName());
        community.setDescription(communityValid.getDescription());
        communityRepository.save(communityValid);
        model.addAttribute("community", community);
        model.addAttribute("posts", postRepository.findByCommunityOwner_Community(community));
        model.addAttribute("username", auth.getName());
        return "redirect:/community/index";
    }

    @PostMapping("/community/delete")
    public String communityDelete(@RequestParam long id) {
        Community community = communityRepository.findById(id).get();
        List<CommunityOwner> communityOwners = communityOwnerRepository.findByCommunity(community);
        List<CommunitySubscriber> communitySubscribers = communitySubscriberRepository.findByCommunity(community);
        List<Post> posts = postRepository.findByCommunityOwner_Community(community);
        List<Comment> comments = new ArrayList<>();
        for (Post post : posts) {
            comments.addAll(commentRepository.findByPost(post));
        }
        commentRepository.deleteAll(comments);
        postRepository.deleteAll(posts);
        communityOwnerRepository.deleteAll(communityOwners);
        communitySubscriberRepository.deleteAll(communitySubscribers);
        communityRepository.delete(community);
        return "redirect:/community/index";
    }

    @PostMapping("/community/details")
    public String communityDetails(@RequestParam long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Community community = communityRepository.findById(id).get();
        User user = userRepository.findUserByUsername(auth.getName());
        List<Post> posts = postRepository.findByCommunityOwner_Community(community);
        CommunitySubscriber communitySubscriber = communitySubscriberRepository.findByCommunityAndUser(community, user);
        if (communitySubscriber == null) model.addAttribute("message", "Подписаться");
        else model.addAttribute("message", "Отписаться");
        model.addAttribute("posts", posts);
        model.addAttribute("community", community);
        model.addAttribute("user", user);
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(auth));
        return "Community/Details";
    }

    @PostMapping("/community/subscribe")
    public String communitySubscribe(@RequestParam long id, Model model) {
        Community community = communityRepository.findById(id).get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(auth.getName());
        List<Post> posts = postRepository.findByCommunityOwner_Community(community);
        model.addAttribute("posts", posts);
        model.addAttribute("community", community);
        model.addAttribute("user", user);
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(auth));
        CommunitySubscriber communitySubscriber = new CommunitySubscriber(user, community);
        community.setSubscribersCount(community.getSubscribersCount() + 1);
        communityRepository.save(community);
        communitySubscriberRepository.save(communitySubscriber);
        return "Community/Details";
    }

    @PostMapping("/community/unSubscribe")
    public String communityUnSubscribe(@RequestParam long id, Model model) {
        Community community = communityRepository.findById(id).get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(auth.getName());
        List<Post> posts = postRepository.findByCommunityOwner_Community(community);
        model.addAttribute("posts", posts);
        model.addAttribute("community", community);
        model.addAttribute("user", user);
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(auth));
        CommunitySubscriber communitySubscriber = communitySubscriberRepository.findByCommunityAndUser(community, user);
        communitySubscriberRepository.delete(communitySubscriber);
        community.setSubscribersCount(community.getSubscribersCount() - 1);
        communityRepository.save(community);
        return "redirect:/community/index";
    }
}