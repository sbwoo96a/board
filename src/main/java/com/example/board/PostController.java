package com.example.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping("/post")
    public String writePost() {
        return "write";
    }

    @PostMapping("/post")
    public String savePost(@ModelAttribute PostForm postForm) {
        log.info("postForm={}", postForm);

        postService.savePost(postForm);
        return "redirect:/posts";
    }

    @GetMapping("/post/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        PostForm postForm = postService.findPostById(id);
        model.addAttribute("postForm", postForm);
        return "edit";
    }

    @PostMapping("/post/{id}/edit")
    public String updatePost(@PathVariable Long id, PostForm postForm) {
        log.info("수정 후 postForm={}", postForm);
        postService.updatePost(postForm);
        return "redirect:/posts";
    }

    @GetMapping("/posts")
    public String findAllPosts(Model model) {
        List<PostForm> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        return "list";
    }

    @GetMapping("/post/{id}")
    public String findPostByID(@PathVariable Long id, Model model) {
        PostForm postForm = postService.findPostById(id);
        model.addAttribute("postForm", postForm);
        return "detail";
    }

    @GetMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }
}
