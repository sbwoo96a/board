package com.example.board.domain.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping("/post")
    public String writePost() {
        return "post/write";
    }

    @PostMapping("/post")
    public String savePost(@ModelAttribute PostForm postForm) throws IOException {
        log.info("postForm={}", postForm);

        postService.savePost(postForm);
        return "redirect:/paging";
    }

    @GetMapping("/post/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        PostForm postForm = postService.findPostById(id);
        model.addAttribute("postForm", postForm);
        return "post/edit";
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
        return "post/list";
    }

    @GetMapping("/post/{id}")
    public String findPostByID(@PathVariable Long id, Model model) {
        PostForm postForm = postService.findPostById(id);
        model.addAttribute("postForm", postForm);
        return "post/detail";
    }

    @GetMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }

    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<PostForm> postForms = postService.paging(pageable);
        int blockLimit = 5;
        int startPage = ((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit)) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < postForms.getTotalPages()) ? startPage + blockLimit - 1 : postForms.getTotalPages();

        model.addAttribute("posts", postForms);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "post/list";
    }
}
