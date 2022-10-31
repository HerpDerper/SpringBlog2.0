package com.example.SpringRealBlog.Controllers;

import com.example.SpringRealBlog.Models.Comment;
import com.example.SpringRealBlog.Models.Post;
import com.example.SpringRealBlog.Models.Thematic;
import com.example.SpringRealBlog.Models.User;
import com.example.SpringRealBlog.Repositories.CommentRepository;
import com.example.SpringRealBlog.Repositories.PostRepository;
import com.example.SpringRealBlog.Repositories.ThematicRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@PreAuthorize("hasAnyAuthority('ADMIN')")
@Controller
public class ThematicController {
    private final ThematicRepository thematicRepository;

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    public ThematicController(ThematicRepository thematicRepository, CommentRepository commentRepository, PostRepository postRepository) {
        this.thematicRepository = thematicRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/thematic/index")
    public String thematicIndex(Model model) {
        Iterable<Thematic> thematics = thematicRepository.findAll();
        model.addAttribute("thematics", thematics);
        model.addAttribute("adminAccess", true);
        return "Thematic/Index";
    }

    @PostMapping("/thematic/editThematic")
    public String thematicEdit(@ModelAttribute("thematic") @Valid Thematic thematicValid, BindingResult bindingResult, Model model) {
        model.addAttribute("adminAccess", true);
        Thematic thematic = thematicRepository.findById(thematicValid.getId()).get();
        if (bindingResult.hasErrors()) return "/Thematic/Edit";
        thematic.setName(thematicValid.getName());
        thematicRepository.save(thematic);
        return "redirect:/thematic/index";
    }

    @PostMapping("/thematic/edit")
    public String thematicEdit(@RequestParam long id, Model model) {
        Thematic thematic = thematicRepository.findById(id).get();
        model.addAttribute("adminAccess", true);
        model.addAttribute("thematic", thematic);
        return "Thematic/Edit";
    }

    @PostMapping("/thematic/delete")
    public String thematicDelete(@RequestParam long id) {
        Thematic thematic = thematicRepository.findById(id).get();
        List<Post> posts = postRepository.findByThematic(thematic);
        List<Comment> comments = new ArrayList<>();
        for (Post post : posts) {
            comments.addAll(commentRepository.findByPost(post));
        }
        commentRepository.deleteAll(comments);
        postRepository.deleteAll(posts);
        thematicRepository.delete(thematic);
        return "redirect:/thematic/index";
    }

    @PostMapping("/thematic/create")
    public String thematicCreate(@ModelAttribute("thematic") @Valid Thematic thematic, BindingResult bindingResult) {
        if (thematicRepository.findByName(thematic.getName()) != null)
            bindingResult.addError(new ObjectError("name", "Название уже занято"));
        if (bindingResult.hasErrors()) return "Thematic/Create";
        thematicRepository.save(thematic);
        return "redirect:/";
    }

    @GetMapping("/thematic/create")
    public String thematicCreate(@ModelAttribute("user") User user) {
        return "Thematic/Create";
    }
}