package com.example.SpringRealBlog.Controllers;

import com.example.SpringRealBlog.AdminCheck;
import com.example.SpringRealBlog.Models.Comment;
import com.example.SpringRealBlog.Models.CommentLike;
import com.example.SpringRealBlog.Models.Post;
import com.example.SpringRealBlog.Models.User;
import com.example.SpringRealBlog.Repositories.CommentLikeRepository;
import com.example.SpringRealBlog.Repositories.CommentRepository;
import com.example.SpringRealBlog.Repositories.PostRepository;
import com.example.SpringRealBlog.Repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final CommentLikeRepository commentLikeRepository;

    public CommentController(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository, CommentLikeRepository commentLikeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.commentLikeRepository = commentLikeRepository;
    }

    @PostMapping("/comment/create")
    public String commentCreate(@RequestParam Long postId, @ModelAttribute("comment") @Valid Comment commentValid, BindingResult bindingResult, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(auth));
        Post post = postRepository.findById(postId).get();
        User user = userRepository.findUserByUsername(auth.getName());
        model.addAttribute("post", post);
        model.addAttribute("user", user);
        if (bindingResult.hasErrors()) {
            model.addAttribute("comments", commentRepository.findByPost(post));
            return "Post/Details";
        }
        Comment comment = new Comment(commentValid.getText(), 0, new Date(), userRepository.findUserByUsername(auth.getName()), postRepository.findById(postId).get());
        commentRepository.save(comment);
        commentValid.setText("");
        model.addAttribute("comments", commentRepository.findByPost(post));
        return "Post/Details";
    }

    @PostMapping("/comment/editComment")
    public String commentEdit(@ModelAttribute("comment") @Valid Comment commentValid, BindingResult bindingResult, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(auth));
        if (bindingResult.hasErrors()) {
            return "Comment/Edit";
        }
        Comment comment = commentRepository.findById(commentValid.getId()).get();
        User user = userRepository.findUserByUsername(auth.getName());
        comment.setText(commentValid.getText());
        commentRepository.save(comment);
        commentValid.setText("");
        Post post = postRepository.findById(comment.getPost().getId()).get();
        model.addAttribute("post", post);
        model.addAttribute("comments", commentRepository.findByPost(post));
        model.addAttribute("user", user);
        return "Post/Details";
    }

    @PostMapping("/comment/edit")
    public String commentEdit(@RequestParam long id, Model model) {
        Comment comment = commentRepository.findById(id).get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(auth));
        model.addAttribute("comment", comment);
        return "Comment/Edit";
    }

    @PostMapping("/comment/delete")
    public String commentDelete(@RequestParam long id, Model model, @ModelAttribute("comment") Comment commentValid) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(auth));
        Comment comment = commentRepository.findById(id).get();
        User user = userRepository.findUserByUsername(auth.getName());
        List<CommentLike> commentLikes = commentLikeRepository.findByComment(comment);
        commentLikeRepository.deleteAll(commentLikes);
        commentRepository.delete(comment);
        Post post = postRepository.findById(comment.getPost().getId()).get();
        model.addAttribute("post", post);
        model.addAttribute("comments", commentRepository.findByPost(post));
        model.addAttribute("user", user);
        return "Post/Details";
    }

    @PostMapping("/comment/likeComment")
    public String commentLike(@ModelAttribute("comment") Comment commentCreate, @RequestParam long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("adminAccess", new AdminCheck().adminAccess(auth));
        User user = userRepository.findUserByUsername(auth.getName());
        Comment comment = commentRepository.findById(id).get();
        Post post = postRepository.findById(comment.getPost().getId()).get();
        CommentLike commentLike = commentLikeRepository.findByCommentAndUser(comment, user);
        model.addAttribute("post", post);
        model.addAttribute("user", user);
        model.addAttribute("comments", commentRepository.findByPost(post));
        if (commentLike == null) {
            comment.setLikeCount(comment.getLikeCount() + 1);
            commentLike = new CommentLike(user, comment);
            commentLikeRepository.save(commentLike);
        } else {
            comment.setLikeCount(comment.getLikeCount() - 1);
            commentLikeRepository.delete(commentLike);
        }
        commentRepository.save(comment);
        return "Post/Details";
    }
}