package com.example.board.domain.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<Object> writeComment(@ModelAttribute CommentDTO commentDTO) {
        log.info("댓글 작성={}", commentDTO);
        Long saveResult = commentService.writeComment(commentDTO);
        if (saveResult != null) {
            //작성 성공하면 댓글 목록을 가져와서 리턴
            //댓글 목록: 해당 게시글의 댓글 전체
            List<CommentDTO> comments = commentService.findComments(commentDTO.getBoardId());
            log.info("댓글 목록={}", comments);
            return new ResponseEntity<>(comments, OK);
        } else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", NOT_FOUND);
        }
    }

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<List<CommentDTO>> findAllComments(@PathVariable Long boardId) {
        List<CommentDTO> comments = commentService.findComments(boardId);
        log.info("댓글 목록={}", comments);
        return new ResponseEntity<>(comments, OK);
    }

    @PostMapping("/comment/edit")
    @ResponseBody
    public String editComment(@RequestBody CommentDTO commentDTO) {
        log.info("수정한 댓글의 id와 content={}", commentDTO);
        log.info("수정한 댓글의 id={}", commentDTO.getId());
        log.info("수정한 댓글의 content={}", commentDTO.getCommentContent());
        commentService.updateComment(commentDTO);
        return "좋다꾸나";
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        log.info("commentId={}", commentId);
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("ok", OK);
    }
}
