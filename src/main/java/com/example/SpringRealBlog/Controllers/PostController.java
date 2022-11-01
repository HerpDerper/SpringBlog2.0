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
public class PostController {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final CommunityOwnerRepository communityOwnerRepository;

    private final CommunityRepository communityRepository;

    private final ThematicRepository thematicRepository;

    private final CommunitySubscriberRepository communitySubscriberRepository;

    public PostController(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository,
                          CommunityOwnerRepository communityOwnerRepository, CommunityRepository communityRepository,
                          ThematicRepository thematicRepository, CommunitySubscriberRepository communitySubscriberRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.communityOwnerRepository = communityOwnerRepository;
        this.communityRepository = communityRepository;
        this.thematicRepository = thematicRepository;
        this.communitySubscriberRepository = communitySubscriberRepository;
    }

    @PostMapping("/post/index")
    public String postIndex(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(auth));
        return "Post/Index";
    }

    @GetMapping("/main")
    public String postMainGet(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<CommunitySubscriber> communitySubscribers = communitySubscriberRepository.findByUser(userRepository.findUserByUsername(auth.getName()));
        List<Community> communities = new ArrayList<>();
        for (CommunitySubscriber subscriber : communitySubscribers) {
            communities.add(communityRepository.findById(subscriber.getCommunity().getId()).get());
        }
        List<Post> posts = new ArrayList<>();
        for (Community community : communities) {
            posts.addAll(postRepository.findByCommunityOwner_Community(community));
        }
        model.addAttribute("posts", posts);
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(auth));
        return "Post/Main";
    }

    @PostMapping("/post/create")
    public String postCreate(@RequestParam long communityId, @ModelAttribute("post") Post post, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(auth));
        model.addAttribute("thematics", thematicRepository.findAll());
        model.addAttribute("community",  communityRepository.findById(communityId).get());
        return "Post/Create";
    }

    @PostMapping("/post/details")
    public String postDetails(@ModelAttribute("comment") Comment comment, @RequestParam long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(auth.getName());
        Post post = postRepository.findById(id).get();
        model.addAttribute("comments", commentRepository.findByPost(post));
        model.addAttribute("post", post);
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(auth));
        model.addAttribute("user", user);
        return "Post/Details";
    }

    @PostMapping("/post/createPost")
    public String postCreate(@RequestParam long communityId,
                             @ModelAttribute("post") @Valid Post postValid, BindingResult bindingResult, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (bindingResult.hasErrors()) {
            model.addAttribute("adminAccess", new AdminCheck().adminAccess(auth));
            model.addAttribute("thematics", thematicRepository.findAll());
            model.addAttribute("community",  communityRepository.findById(communityId).get());
            return "Post/Create";
        }
        CommunityOwner communityOwner = communityOwnerRepository.findByCommunityAndUser(communityRepository.findById(communityId).get(),
                userRepository.findUserByUsername(auth.getName()));
        postValid.setCommunityOwner(communityOwner);
        postValid.setDateCreation(new Date());
        postRepository.save(postValid);
        return "redirect:/post/index";
    }

    @PostMapping("/post/edit")
    public String postEdit(@RequestParam long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(auth));
        model.addAttribute("thematics", thematicRepository.findAll());
        Post post = postRepository.findById(id).get();
        model.addAttribute("post", post);
        return "Post/Edit";
    }

    @PostMapping("/post/editPost")
    public String postEdit(@ModelAttribute("comment") Comment comment, @ModelAttribute("post") @Valid Post postValid, BindingResult bindingResult, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Post post = postRepository.findById(postValid.getId()).get();
        if (bindingResult.hasErrors()) {
            model.addAttribute("adminAccess", new AdminCheck().adminAccess(auth));
            model.addAttribute("thematics", thematicRepository.findAll());
            return "Post/Edit";
        }
        post.setThematic(postValid.getThematic());
        post.setDescription(postValid.getDescription());
        post.setText(postValid.getText());
        postRepository.save(post);
        model.addAttribute("post", post);
        model.addAttribute("comments", commentRepository.findByPost(post));
        model.addAttribute("username", auth.getName());
        return "Post/Details";
    }

    @PostMapping("/post/delete")
    public String postDelete(@RequestParam long id) {
        Post post = postRepository.findById(id).get();
        List<Comment> comments = commentRepository.findByPost(post);
        commentRepository.deleteAll(comments);
        postRepository.delete(post);
        return "redirect:/post/index";
    }

    @GetMapping("/post/index")
    public String postFilter(@RequestParam(required = false) String description, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(auth));
        Iterable<Post> posts;
        if (description != null && !description.equals("")) posts = postRepository.findByDescriptionContains(description);
        else posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "Post/Index";
    }

    @PostMapping("/post/likePost")
    public String postLike(@ModelAttribute("comment") Comment comment, @RequestParam long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(auth));
        User user = userRepository.findUserByUsername(auth.getName());
        Post post = postRepository.findById(id).get();
        model.addAttribute("post", post);
        model.addAttribute("user", user);
        model.addAttribute("comments", commentRepository.findByPost(post));
        if (post.getLikedUsers().contains(user)) {
            post.setLikeCount(post.getLikeCount() - 1);
            post.getLikedUsers().remove(user);
        } else {
            post.setLikeCount(post.getLikeCount() + 1);
            post.getLikedUsers().add(user);
        }
        postRepository.save(post);
        return "Post/Details";
    }
}