package com.example.board.domain.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/board/{id}/comment")
    public String writeComment(@PathVariable Long id, CommentDTO commentDTO) {
        commentService.writeComment(commentDTO, id);

        return "redirect:/post/" + id;
    }

    @GetMapping("/board/{id}/comment/{commentId}/remove")
    public String deleteComment(@PathVariable(name = "id") Long id, @PathVariable(name = "commentId") Long commentId) {
        log.info("commentId={}", commentId);
        commentService.deleteComment(commentId);
        return "redirect:/post/" + id;
    }
}
